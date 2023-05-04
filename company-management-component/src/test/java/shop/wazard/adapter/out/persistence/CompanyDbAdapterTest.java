package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = "shop.wazard.entity.*")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {CompanyDbAdapter.class, CompanyMapper.class, AccountMapper.class, CompanyJpaRepository.class, AccountForCompanyManagementJpaRepository.class, RelationRepository.class})
class CompanyDbAdapterTest {

    @MockBean
    private CompanyMapper companyMapper;
    @MockBean
    private AccountMapper accountMapper;
    @Autowired
    private CompanyJpaRepository companyJpaRepository;
    @Autowired
    private AccountForCompanyManagementJpaRepository accountForCompanyManagementJpaRepository;
    @Autowired
    private RelationRepository relationRepository;

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
        Mockito.when(companyMapper.toCompanyJpa(any(Company.class))).thenReturn(companyJpa);

        AccountJpa accountJpa = accountForCompanyManagementJpaRepository.findByEmail(account.getEmail());
        CompanyJpa result = companyJpaRepository.save(companyMapper.toCompanyJpa(company));

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
        Mockito.when(companyMapper.saveRelationInfo(any(AccountJpa.class), any(CompanyJpa.class)))
                .thenReturn(companyAccountRelJpa);

        CompanyAccountRelJpa result = relationRepository.save(companyMapper.saveRelationInfo(accountJpa, companyJpa));

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getAccountJpa(), accountJpa),
                () -> Assertions.assertEquals(result.getCompanyJpa(), companyJpa)
        );
    }

}