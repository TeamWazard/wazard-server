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
        AccountJpa accountJpa = setDefaultEmployerAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

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

    @Test
    @DisplayName("고용주 - 업장 삭제 - CompanyAccountRelJpa 상태 값 변경")
    public void deleteCompanyAccountRelJpaSuccess() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployerAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

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
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        CompanyJpa savedCompanyJpa = companyJpaForManagementRepository.save(companyJpa);
        savedCompanyJpa.delete();
        em.flush();

        // then
        Assertions.assertEquals("INACTIVE", savedCompanyJpa.getStateJpa().getStatus());
    }

    @Test
    @DisplayName("고용주 - 운영 업장 리스트 조회 - 리스트 조회")
    public void getOwnedCompanyList() throws Exception {
        // given
        Long accountId = setDefaultOwnedCompanyList();

        // when
        List<CompanyJpa> result = companyJpaForManagementRepository.findOwnedCompanyList(accountId);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.get(0).getCompanyName(), "companyName1"),
                () -> Assertions.assertEquals(result.get(1).getCompanyName(), "companyName2"),
                () -> Assertions.assertEquals(result.get(2).getCompanyName(), "companyName3")
        );
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

    private AccountJpa setDefaultEmployerAccountJpa() {
        return AccountJpa.builder()
                .email("test@email.com")
                .password("testPwd")
                .userName("testName")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .stateJpa(BaseEntity.StateJpa.ACTIVE)
                .roles("EMPLOYER")
                .build();
    }

    private Long setDefaultOwnedCompanyList() {
        AccountJpa accountJpa = setDefaultEmployerAccountJpa();
        CompanyJpa companyJpa1 = CompanyJpa.builder()
                .companyName("companyName1")
                .companyAddress("companyAddress1")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test1.com")
                .build();
        CompanyJpa companyJpa2 = CompanyJpa.builder()
                .companyName("companyName2")
                .companyAddress("companyAddress2")
                .companyContact("02-222-2222")
                .salaryDate(2)
                .logoImageUrl("www.test2.com")
                .build();
        CompanyJpa companyJpa3 = CompanyJpa.builder()
                .companyName("companyName3")
                .companyAddress("companyAddress3")
                .companyContact("02-333-3333")
                .salaryDate(3)
                .logoImageUrl("www.test3.com")
                .build();

        AccountJpa savedAccountJpa = accountJpaForCompanyManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa1 = companyJpaForManagementRepository.save(companyJpa1);
        CompanyJpa savedCompanyJpa2 = companyJpaForManagementRepository.save(companyJpa2);
        CompanyJpa savedCompanyJpa3 = companyJpaForManagementRepository.save(companyJpa3);

        rosterJpaForCompanyManagementRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa1)
                .relationTypeJpa(RelationTypeJpa.EMPLOYER)
                .build());
        rosterJpaForCompanyManagementRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa2)
                .relationTypeJpa(RelationTypeJpa.EMPLOYER)
                .build());
        rosterJpaForCompanyManagementRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa3)
                .relationTypeJpa(RelationTypeJpa.EMPLOYER)
                .build());
        em.flush();
        return savedAccountJpa.getId();
    }

}