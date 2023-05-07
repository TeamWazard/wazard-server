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
import shop.wazard.application.port.domain.AccountForManagement;
import shop.wazard.application.port.domain.CompanyForManagement;
import shop.wazard.application.port.domain.CompanyInfo;
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
@ContextConfiguration(classes = {EntityManager.class, CompanyDbAdapter.class, CompanyMapperForManagement.class, AccountMapperForManagement.class, CompanyJpaRepository.class, AccountForCompanyManagementJpaRepository.class, RelationRepository.class})
class CompanyForManagementDbAdapterTest {

    @Autowired
    private CompanyMapperForManagement companyMapperForManagement;
    @MockBean
    private AccountMapperForManagement accountMapperForManagement;
    @Autowired
    private CompanyJpaRepository companyJpaRepository;
    @Autowired
    private AccountForCompanyManagementJpaRepository accountForCompanyManagementJpaRepository;
    @Autowired
    private RelationRepository relationRepository;
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
                .state(BaseEntity.State.ACTIVE)
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
                        shop.wazard.application.port.domain.CompanyInfo.builder()
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
        AccountJpa accountJpa = accountForCompanyManagementJpaRepository.findByEmail(accountForManagement.getEmail());
        CompanyJpa result = companyJpaRepository.save(companyMapperForManagement.toCompanyJpa(companyForManagement));
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
        CompanyAccountRelJpa companyAccountRelJpa = CompanyAccountRelJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .build();

        // when
        accountForCompanyManagementJpaRepository.save(accountJpa);
        companyJpaRepository.save(companyJpa);
        CompanyAccountRelJpa result = relationRepository.save(companyMapperForManagement.saveRelationInfo(accountJpa, companyJpa, RelationTypeJpa.EMPLOYER));
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
        CompanyJpa savedCompanyJpa = companyJpaRepository.save(companyJpa);
        CompanyJpa realCompanyJpa = companyJpaRepository.findById(savedCompanyJpa.getId()).get();
        companyMapperForManagement.updateCompanyInfo(realCompanyJpa, changedCompanyForManagement);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getCompanyName(), realCompanyJpa.getCompanyName()),
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getCompanyAddress(), realCompanyJpa.getCompanyAddress()),
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getCompanyContact(), realCompanyJpa.getCompanyContact()),
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getLogoImageUrl(), realCompanyJpa.getLogoImageUrl()),
                () -> Assertions.assertEquals(changedCompanyForManagement.getCompanyInfo().getSalaryDate(), realCompanyJpa.getSalaryDate())
        );
    }

    @Test
    @DisplayName("고용주 - 업장 삭제 - CompanyJpa 상태 값 변경")
    public void deleteCompanySuccess() throws Exception {
        // given
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        companyJpaRepository.save(companyJpa);
        CompanyJpa result = companyJpaRepository.findById(companyJpa.getId()).get();
        companyJpa.delete();
        em.flush();

        // then
        Assertions.assertEquals(companyJpa.getState().getStatus(), "INACTIVE");
    }

}