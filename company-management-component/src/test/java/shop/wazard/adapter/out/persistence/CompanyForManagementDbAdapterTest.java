package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.domain.AccountForManagement;
import shop.wazard.application.domain.CompanyForManagement;
import shop.wazard.application.domain.CompanyInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RelationTypeJpa;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EntityManager.class, CompanyManagementDbAdapterForManagementForManagement.class, CompanyForCompanyManagementMapper.class, AccountForCompanyManagementMapper.class, CompanyJpaForManagementRepository.class, AccountJpaForCompanyManagementRepository.class, CompanyAccountRelJpaRepository.class})
class CompanyForManagementDbAdapterTest {

    @Autowired
    private CompanyForCompanyManagementMapper companyForCompanyManagementMapper;
    @MockBean
    private AccountForCompanyManagementMapper accountForCompanyManagementMapper;
    @Autowired
    private CompanyJpaForManagementRepository companyJpaForManagementRepository;
    @Autowired
    private AccountJpaForCompanyManagementRepository accountJpaForCompanyManagementRepository;
    @Autowired
    private CompanyAccountRelJpaRepository companyAccountRelJpaRepository;
    @Autowired
    private EntityManager em;

    private AccountJpa setDefaultAccountJpa() {
        return AccountJpa.builder()
                .email("test@email.com")
                .password("testPwd")
                .userName("testName")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .stateJpa(BaseEntity.StateJpa.ACTIVE)
                .companyAccountRelJpaList(null)
                .build();
    }

    private CompanyJpa setDefaultCompanyJpa() {
        return CompanyJpa.builder()
                .companyName("companyName")
                .companyAddress("companyAddress")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test.com")
                .build();
    }

    @Test
    @DisplayName("고용주 - 업장 등록 - CompanyJpa 저장")
    public void saveCompanyJpaSuccess() throws Exception {
        // given
        AccountForManagement accountForManagement = AccountForManagement.builder()
                .id(1L)
                .roles("EMPLOYER")
                .email("test@email.com")
                .userName("name")
                .build();
        CompanyForManagement companyForManagement = CompanyForManagement.builder()
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName("companyName")
                                .companyAddress("companyAddress")
                                .companyContact("02-111-1111")
                                .salaryDate(1)
                                .logoImageUrl("www.test.com")
                                .build()
                )
                .build();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa accountJpa = accountJpaForCompanyManagementRepository.findByEmail(accountForManagement.getEmail());
        CompanyJpa result = companyJpaForManagementRepository.save(companyForCompanyManagementMapper.toCompanyJpa(companyForManagement));
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getCompanyName(), companyForManagement.getCompanyInfo().getCompanyName()),
                () -> Assertions.assertEquals(result.getCompanyAddress(), companyForManagement.getCompanyInfo().getCompanyAddress()),
                () -> Assertions.assertEquals(result.getCompanyContact(), companyForManagement.getCompanyInfo().getCompanyContact()),
                () -> Assertions.assertEquals(result.getSalaryDate(), companyForManagement.getCompanyInfo().getSalaryDate()),
                () -> Assertions.assertEquals(result.getLogoImageUrl(), companyForManagement.getCompanyInfo().getLogoImageUrl())
        );
    }

    @Test
    @DisplayName("고용주 - 업장 등록 - CompanyAccountRelJpa 저장")
    public void saveCompanyAccountRelJpaSuccess() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        accountJpaForCompanyManagementRepository.save(accountJpa);
        companyJpaForManagementRepository.save(companyJpa);
        CompanyAccountRelJpa companyAccountRelJpa = companyForCompanyManagementMapper.saveRelationInfo(accountJpa, companyJpa, RelationTypeJpa.EMPLOYER);
        CompanyAccountRelJpa result = companyAccountRelJpaRepository.save(companyAccountRelJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getAccountJpa(), accountJpa),
                () -> Assertions.assertEquals(result.getCompanyJpa(), companyJpa)
        );
    }
    
    @Test
    @DisplayName("고용주 - 업장 정보 수정 - CompanyAccountRel 수정")
    public void updateCompanyInfoSuccess() throws Exception {
        // given
        CompanyJpa companyJpa = setDefaultCompanyJpa();
        CompanyForManagement changedCompanyForManagement = CompanyForManagement.builder()
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName("testName")
                                .companyAddress("testAddress")
                                .companyContact("031-123-1234")
                                .logoImageUrl("testLogoUrl")
                                .salaryDate(1)
                                .build()
                )
                .build();

        // when
        CompanyJpa savedCompanyJpa = companyJpaForManagementRepository.save(companyJpa);
        CompanyJpa result = companyJpaForManagementRepository.findById(savedCompanyJpa.getId()).get();
        companyForCompanyManagementMapper.updateCompanyInfo(result, changedCompanyForManagement);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getCompanyName(), result.getCompanyName()),
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getCompanyAddress(), result.getCompanyAddress()),
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getCompanyContact(), result.getCompanyContact()),
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getLogoImageUrl(), result.getLogoImageUrl()),
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getSalaryDate(), result.getSalaryDate())
        );
    }


    // TODO : 중간 테이블에 accountId가 저장되지 않음 , 테스트는 에러 발생 중
    @Test
    @DisplayName("고용주 - 업장 삭제 - CompanyAccountRelJpa 상태 값 변경")
    public void deleteCompanySuccess() throws Exception {
        // given
        CompanyJpa companyJpa = setDefaultCompanyJpa();
        AccountJpa accountJpa = setDefaultAccountJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForCompanyManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForManagementRepository.save(companyJpa);
        CompanyAccountRelJpa result = companyAccountRelJpaRepository.save(CompanyAccountRelJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa)
                .relationTypeJpa(RelationTypeJpa.EMPLOYER)
                .build());
        em.flush();

        // then
        Assertions.assertDoesNotThrow(() -> companyAccountRelJpaRepository.deleteCompany(result.getId()));
    }

}