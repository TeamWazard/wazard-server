package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.out.LoadAccountForCompanyManagementPort;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

@Repository
@Transactional(readOnly = true)
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
    @Transactional
    public void saveCompany(String email, Company company) {
        CompanyJpa companyJpa = companyMapper.toCompanyJpa(company);
        AccountJpa accountJpa = accountForCompanyManagementJpaRepository.findByEmail(email);
        CompanyAccountRelJpa companyAccountRelJpa = companyMapper.saveRelationInfo(accountJpa, companyJpa);
        companyJpaRepository.save(companyJpa);
        relationRepository.save(companyAccountRelJpa);
    }

    @Override
    public Company findCompanyById(Long id) {
        CompanyJpa companyJpa = companyJpaRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        return companyMapper.toCompanyDomain(companyJpa);
    }

    @Override
    @Transactional
    public void updateCompanyInfo(Company company) {
        CompanyJpa companyJpa = companyJpaRepository.findById(company.getId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        companyMapper.updateCompanyInfo(companyJpa, company);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId) {
    }
}
