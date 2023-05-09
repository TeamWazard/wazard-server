package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForManagement;
import shop.wazard.application.domain.CompanyForManagement;
import shop.wazard.application.port.out.AccountForCompanyManagementPort;
import shop.wazard.application.port.out.CompanyForManagementPort;
import shop.wazard.application.port.out.RosterForCompanyManagementPort;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RelationTypeJpa;
import shop.wazard.entity.company.RosterJpa;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CompanyManagementDbAdapter implements CompanyForManagementPort, AccountForCompanyManagementPort, RosterForCompanyManagementPort {

    private final CompanyForCompanyManagementMapper companyForCompanyManagementMapper;
    private final AccountForCompanyManagementMapper accountForCompanyManagementMapper;
    private final CompanyJpaForManagementRepository companyJpaForManagementRepository;
    private final AccountJpaForCompanyManagementRepository accountJpaForCompanyManagementRepository;
    private final RosterJpaForCompanyManagementRepository rosterJpaForCompanyManagementRepository;

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
        RosterJpa rosterJpa = companyForCompanyManagementMapper.saveRelationInfo(accountJpa, companyJpa, RelationTypeJpa.EMPLOYER);
        companyJpaForManagementRepository.save(companyJpa);
        rosterJpaForCompanyManagementRepository.save(rosterJpa);
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
    public void deleteCompany(Long companyId) {
        CompanyJpa companyJpa = companyJpaForManagementRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        companyForCompanyManagementMapper.deleteCompany(companyJpa);
    }

    @Override
    @Transactional
    public void deleteRoster(Long companyId) {
        CompanyJpa companyJpa = companyJpaForManagementRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        rosterJpaForCompanyManagementRepository.deleteCompanyAccountRel(companyId);
    }

    @Override
    public List<CompanyForManagement> getOwnedCompanyList(Long accountId) {
        return null;
    }

}
