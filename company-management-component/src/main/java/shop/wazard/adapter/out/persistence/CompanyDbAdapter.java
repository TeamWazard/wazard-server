package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.domain.AccountForManagement;
import shop.wazard.application.port.domain.CompanyForManagement;
import shop.wazard.application.port.out.*;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RelationTypeJpa;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CompanyDbAdapter implements LoadCompanyPort, SaveCompanyPort, UpdateCompanyPort, LoadAccountForCompanyManagementPort, UpdateCompanyAccountRelForCompanyManagementPort {

    private final CompanyMapperForManagement companyMapperForManagement;
    private final AccountMapperForManagement accountMapperForManagement;
    private final CompanyJpaRepository companyJpaRepository;
    private final AccountForCompanyManagementJpaRepository accountForCompanyManagementJpaRepository;
    private final RelationRepository relationRepository;

    @Override
    public AccountForManagement findAccountByEmail(String email) {
        AccountJpa accountJpa = accountForCompanyManagementJpaRepository.findByEmail(email);
        return accountMapperForManagement.toAccount(accountJpa);
    }

    @Override
    @Transactional
    public void saveCompany(String email, CompanyForManagement companyForManagement) {
        CompanyJpa companyJpa = companyMapperForManagement.toCompanyJpa(companyForManagement);
        AccountJpa accountJpa = accountForCompanyManagementJpaRepository.findByEmail(email);
        CompanyAccountRelJpa companyAccountRelJpa = companyMapperForManagement.saveRelationInfo(accountJpa, companyJpa, RelationTypeJpa.EMPLOYER);
        companyJpaRepository.save(companyJpa);
        relationRepository.save(companyAccountRelJpa);
    }

    @Override
    public CompanyForManagement findCompanyById(Long id) {
        CompanyJpa companyJpa = companyJpaRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        return companyMapperForManagement.toCompanyDomain(companyJpa);
    }

    @Override
    @Transactional
    public void updateCompanyInfo(CompanyForManagement companyForManagement) {
        CompanyJpa companyJpa = companyJpaRepository.findById(companyForManagement.getId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        companyMapperForManagement.updateCompanyInfo(companyJpa, companyForManagement);
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId) {
        CompanyJpa companyJpa = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        companyMapperForManagement.deleteCompany(companyJpa);
    }

}
