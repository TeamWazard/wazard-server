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
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.domain.CompanyInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EntityManager.class, CompanyDbAdapter.class, CompanyMapperForManagement.class, AccountMapperForManagement.class, CompanyJpaRepository.class, AccountForCompanyManagementJpaRepository.class, RelationRepository.class})
class CompanyDbAdapterTest {

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

    @Test
    @DisplayName("고용주 - 업장 등록 - CompanyJpa 저장")
    public void saveCompanyJpaSuccess() throws Exception {
        // given
        Account account = Account.builder()
                .id(1L)
                .roles("EMPLOYER")
                .email("test@email.com")
                .userName("name")
                .build();
        Company company = Company.builder()
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
        CompanyJpa companyJpa = CompanyJpa.builder()
                .companyName("companyName")
                .companyAddress("companyAddress")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test.com")
                .build();

        // when
        AccountJpa accountJpa = accountForCompanyManagementJpaRepository.findByEmail(account.getEmail());
        CompanyJpa result = companyJpaRepository.save(companyMapperForManagement.toCompanyJpa(company));
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getCompanyName(), company.getCompanyInfo().getCompanyName()),
                () -> Assertions.assertEquals(result.getCompanyAddress(), company.getCompanyInfo().getCompanyAddress()),
                () -> Assertions.assertEquals(result.getCompanyContact(), company.getCompanyInfo().getCompanyContact()),
                () -> Assertions.assertEquals(result.getSalaryDate(), company.getCompanyInfo().getSalaryDate()),
                () -> Assertions.assertEquals(result.getLogoImageUrl(), company.getCompanyInfo().getLogoImageUrl())
        );
    }

    @Test
    @DisplayName("고용주 - 업장 등록 - CompanyAccountRelJpa 저장")
    public void saveCompanyAccountRelJpaSuccess() throws Exception {
        // given
        AccountJpa accountJpa = AccountJpa.builder()
                .email("test@email.com")
                .password("testPwd")
                .userName("testName")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2022, 1, 1))
                .state(BaseEntity.State.ACTIVE)
                .companyAccountRelJpaList(null)
                .build();
        CompanyJpa companyJpa = CompanyJpa.builder()
                .companyName("companyName")
                .companyAddress("companyAddress")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test.com")
                .build();
        CompanyAccountRelJpa companyAccountRelJpa = CompanyAccountRelJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .build();

        // when
        accountForCompanyManagementJpaRepository.save(accountJpa);
        companyJpaRepository.save(companyJpa);
        CompanyAccountRelJpa result = relationRepository.save(companyMapperForManagement.saveRelationInfo(accountJpa, companyJpa));
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getAccountJpa(), accountJpa),
                () -> Assertions.assertEquals(result.getCompanyJpa(), companyJpa)
        );
    }
    
    @Test
    @DisplayName("고용주 - 업장 수정 - 성공")
    public void updateCompanyInfoSuccess() throws Exception {
        // given
        CompanyJpa companyJpa = CompanyJpa.builder()
                .companyName("testN")
                .companyAddress("testA")
                .companyContact("031-123-1234")
                .logoImageUrl("testLU")
                .salaryDate(1)
                .build();
        Company changedCompany = Company.builder()
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

        //when
        CompanyJpa savedCompanyJpa = companyJpaRepository.save(companyJpa);
        CompanyJpa realCompanyJpa = companyJpaRepository.findById(savedCompanyJpa.getId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        companyMapperForManagement.updateCompanyInfo(realCompanyJpa, changedCompany);
        em.flush();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getCompanyName(), realCompanyJpa.getCompanyName()),
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getCompanyAddress(), realCompanyJpa.getCompanyAddress()),
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getCompanyContact(), realCompanyJpa.getCompanyContact()),
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getLogoImageUrl(), realCompanyJpa.getLogoImageUrl()),
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getSalaryDate(), realCompanyJpa.getSalaryDate())
        );
    }

}