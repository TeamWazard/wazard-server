package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForManagement;
import shop.wazard.application.domain.CompanyForManagement;
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
class CompanyManagementDbAdapterForManagementForManagement implements LoadCompanyForManagementPort, SaveCompanyForManagementPort, UpdateCompanyPort, LoadAccountForCompanyManagementPort, UpdateCompanyAccountRelForCompanyManagementPort {

    private final CompanyForCompanyManagementMapper companyForCompanyManagementMapper;
    private final AccountForCompanyManagementMapper accountForCompanyManagementMapper;
    private final CompanyJpaForManagementRepository companyJpaForManagementRepository;
    private final AccountJpaForCompanyManagementRepository accountJpaForCompanyManagementRepository;
    private final CompanyAccountRelJpaRepository companyAccountRelJpaRepository;

    @Override
    public AccountForManagement findAccountByEmail(String email) {
        AccountJpa accountJpa = accountJpaForCompanyManagementRepository.findByEmail(email);
        return accountForCompanyManagementMapper.toAccount(accountJpa);
    }

    @Override
    @Transactional
    public void saveCompany(String email, CompanyForManagement companyForManagement) {
        CompanyJpa companyJpa = companyForCompanyManagementMapper.toCompanyJpa(companyForManagement);
        AccountJpa accountJpa = accountJpaForCompanyManagementRepository.findByEmail(email);
        CompanyAccountRelJpa companyAccountRelJpa = companyForCompanyManagementMapper.saveRelationInfo(accountJpa, companyJpa, RelationTypeJpa.EMPLOYER);
        companyJpaForManagementRepository.save(companyJpa);
        companyAccountRelJpaRepository.save(companyAccountRelJpa);
    }

    @Override
    public CompanyForManagement findCompanyById(Long id) {
        CompanyJpa companyJpa = companyJpaForManagementRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        return companyForCompanyManagementMapper.toCompanyDomain(companyJpa);
    }

    @Override
    @Transactional
    public void updateCompanyInfo(CompanyForManagement companyForManagement) {
        CompanyJpa companyJpa = companyJpaForManagementRepository.findById(companyForManagement.getId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        companyForCompanyManagementMapper.updateCompanyInfo(companyJpa, companyForManagement);
    }

    @Override
    @Transactional
    public void deleteCompanyAccountRel(Long companyId) {
        CompanyJpa companyJpa = companyJpaForManagementRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        companyAccountRelJpaRepository.deleteCompany(companyId);
    }

}
