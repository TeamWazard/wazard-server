package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.out.*;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;

@Repository
@RequiredArgsConstructor
class CompanyDbAdapter implements LoadCompanyPort, SaveCompanyPort, SaveCompanyAccountRelPort, UpdateCompanyPort, LoadAccountPort {

    private CompanyMapper companyMapper;
    private CompanyJpaRepository companyJpaRepository;
    private CompanyAccountRelJpaRepository companyAccountRelJpaRepository;
    private AccountJpaRepository accountJpaRepository;

    @Override
    public Account findAccountByEmail(String email) {
        return companyMapper.toAccountDomain(accountJpaRepository.findByEmail(email));
    }

    @Override
    public void saveCompany(Company company) {
        companyJpaRepository.save( companyMapper.toCompanyEntity(company));
    }

    @Override
    public void saveCompanyAccountRel(Company company, Account account) {
        final CompanyJpa companyJpa = companyMapper.toCompanyEntity(company);
        final AccountJpa accountJpa = companyMapper.toAccountEntity(account);
        final CompanyAccountRelJpa companyAccountRelJpa = CompanyAccountRelJpa.builder()
                .companyJpa(companyJpa)
                .accountJpa(accountJpa)
                .build();

        companyAccountRelJpaRepository.save(companyAccountRelJpa);
    }
}
