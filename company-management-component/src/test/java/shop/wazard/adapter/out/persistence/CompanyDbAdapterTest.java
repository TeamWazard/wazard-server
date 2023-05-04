package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = "shop.wazard.entity.*")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {CompanyDbAdapter.class, AccountMapper.class, CompanyMapper.class, CompanyJpaRepository.class, AccountJpaRepository.class, RelationRepository.class})
class CompanyDbAdapterTest {

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private CompanyJpaRepository companyJpaRepository;
    @Autowired
    private AccountJpaRepository accountJpaRepository;
    @Autowired
    private RelationRepository relationRepository;

    @Test
    @DisplayName("고용주 - 업장 등록 - 성공")
    public void registerCompanySuccess() throws Exception {
        // given
        Account account = Account.builder()
                .id(1L)
                .roles("EMPLOYER")
                .email("test@email.com")
                .userName("testName")
                .build();
        Company company = Company.builder()
                .companyInfo(
                        shop.wazard.application.port.domain.CompanyInfo.builder()
                                .companyName("testCompanyName")
                                .companyAddress("testCompanyAddress")
                                .companyContact("031-432-4321")
                                .salaryDate(1)
                                .build()
                )
                .build();

        // when
        AccountJpa accountJpa = accountJpaRepository.findByEmail(account.getEmail());
        CompanyJpa companyJpaResult = companyJpaRepository.save(companyMapper.toCompanyJpa(company));

        // then
        Assertions.assertEquals(companyJpaResult.getCompanyName(), company.getCompanyInfo().getCompanyName());
    }

}