package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.out.LoadAccountForCompanyManagementPort;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;

@Repository
@RequiredArgsConstructor
class CompanyDbAdapter implements LoadCompanyPort, SaveCompanyPort, UpdateCompanyPort, LoadAccountForCompanyManagementPort {

    private final CompanyMapper companyMapper;
    private final AccountMapper accountMapper;
    private final CompanyJpaRepository companyJpaRepository;
    private final AccountForCompanyManagementJpaRepository accountForCompanyManagementJpaRepository;
    private final RelationRepository relationRepository;

    @Override
    public Account findAccountByEmail(String email) {
        AccountJpa accountJpa = accountForCompanyManagementJpaRepository.findByEmail(email);
        return accountMapper.toAccount(accountJpa);
    }

    @Override
    public void saveCompany(String email, Company company) {
        CompanyJpa companyJpa = companyMapper.toCompanyJpa(company);
        AccountJpa accountJpa = accountForCompanyManagementJpaRepository.findByEmail(email);
        CompanyAccountRelJpa companyAccountRelJpa = companyMapper.saveRelationInfo(accountJpa, companyJpa);
        companyJpaRepository.save(companyJpa);
        relationRepository.save(companyAccountRelJpa);
    }

}
