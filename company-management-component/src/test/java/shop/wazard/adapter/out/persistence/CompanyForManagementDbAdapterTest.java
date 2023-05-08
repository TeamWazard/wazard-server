package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RelationTypeJpa;
import shop.wazard.entity.company.RosterJpa;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EntityManager.class, CompanyManagementDbAdapter.class, CompanyForCompanyManagementMapper.class, AccountForCompanyManagementMapper.class, CompanyJpaForManagementRepository.class, AccountJpaForCompanyManagementRepository.class, RosterJpaForCompanyManagementRepository.class})
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
    private RosterJpaForCompanyManagementRepository rosterJpaForCompanyManagementRepository;
    @Autowired
    private EntityManager em;


    private CompanyJpa companyJpa;
    private AccountJpa accountJpa;

    @BeforeEach
    private void setDefaultAccountJpa() {
        this.accountJpa = AccountJpa.builder()
                .email("test@email.com")
                .password("testPwd")
                .userName("testName")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .stateJpa(BaseEntity.StateJpa.ACTIVE)
                .build();
        this.companyJpa = CompanyJpa.builder()
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

        // when
        accountJpaForCompanyManagementRepository.save(accountJpa);
        companyJpaForManagementRepository.save(companyJpa);
        RosterJpa rosterJpa = companyForCompanyManagementMapper.saveRelationInfo(accountJpa, companyJpa, RelationTypeJpa.EMPLOYER);
        RosterJpa result = rosterJpaForCompanyManagementRepository.save(rosterJpa);
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

    @Test
    @DisplayName("고용주 - 업장 삭제 - CompanyAccountRelJpa 상태 값 변경")
    public void deleteCompanyAccountRelJpaSuccess() throws Exception {
        // given

        // when
        AccountJpa savedAccountJpa = accountJpaForCompanyManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForManagementRepository.save(companyJpa);
        RosterJpa rosterJpa = rosterJpaForCompanyManagementRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa)
                .relationTypeJpa(RelationTypeJpa.EMPLOYER)
                .build());
        rosterJpaForCompanyManagementRepository.deleteCompanyAccountRel(savedCompanyJpa.getId());
        List<RosterJpa> resultList = rosterJpaForCompanyManagementRepository.findAll();
        em.flush();

        // then
        Assertions.assertEquals("INACTIVE", resultList.get(0).getStateJpa().getStatus());
    }

    @Test
    @DisplayName("고용주 - 업장 삭제 - CompanyJpa 상태 값 변경")
    public void deleteCompanyJpaSuccess() throws Exception {
        // given

        // when
        CompanyJpa savedCompanyJpa = companyJpaForManagementRepository.save(companyJpa);
        savedCompanyJpa.delete();
        em.flush();

        // then
        Assertions.assertEquals("INACTIVE", savedCompanyJpa.getStateJpa().getStatus());
    }

}