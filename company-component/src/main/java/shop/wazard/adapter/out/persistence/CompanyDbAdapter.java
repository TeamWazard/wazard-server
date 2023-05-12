package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AccountForCompany;
import shop.wazard.application.domain.Company;
import shop.wazard.application.port.out.AccountForCompanyPort;
import shop.wazard.application.port.out.CompanyPort;
import shop.wazard.application.port.out.RosterForCompanyPort;
import shop.wazard.dto.GetBelongedCompanyResDto;
import shop.wazard.dto.GetOwnedCompanyResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RosterJpa;
import shop.wazard.entity.company.RosterTypeJpa;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import java.util.List;

@Repository
@RequiredArgsConstructor
class CompanyDbAdapter implements CompanyPort, AccountForCompanyPort, RosterForCompanyPort {

    private final CompanyMapper companyMapper;
    private final AccountForCompanyMapper accountForCompanyMapper;
    private final CompanyJpaRepository companyJpaRepository;
    private final AccountJpaForCompanyRepository accountJpaForCompanyRepository;
    private final RosterJpaForCompanyRepository rosterJpaForCompanyRepository;

    @Override
    public AccountForCompany findAccountByEmail(String email) {
        AccountJpa accountJpa = accountJpaForCompanyRepository.findByEmail(email);
        return accountForCompanyMapper.toAccount(accountJpa);
    }

    @Override
    public void saveCompany(String email, Company company) {
        CompanyJpa companyJpa = companyMapper.toCompanyJpa(company);
        AccountJpa accountJpa = accountJpaForCompanyRepository.findByEmail(email);
        RosterJpa rosterJpa = companyMapper.saveRelationInfo(accountJpa, companyJpa, RosterTypeJpa.EMPLOYER);
        companyJpaRepository.save(companyJpa);
        rosterJpaForCompanyRepository.save(rosterJpa);
    }

    @Override
    public Company findCompanyById(Long id) {
        CompanyJpa companyJpa = companyJpaRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        return companyMapper.toCompanyDomain(companyJpa);
    }

    @Override
    public void updateCompanyInfo(Company company) {
        CompanyJpa companyJpa = companyJpaRepository.findById(company.getId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        companyMapper.updateCompanyInfo(companyJpa, company);
    }

    @Override
    public void deleteRoster(Long companyId) {
        CompanyJpa companyJpa = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        rosterJpaForCompanyRepository.deleteCompanyAccountRel(companyId);
        companyJpaRepository.deleteCompany(companyId);
    }

    @Override
    public List<GetOwnedCompanyResDto> getOwnedCompanyList(Long accountId) {
        List<CompanyJpa> ownedCompanyJpaList = companyJpaRepository.findOwnedCompanyList(accountId);
        return companyMapper.toOwnedCompanyList(ownedCompanyJpaList);
    }

    @Override
    public List<GetBelongedCompanyResDto> getBelongedCompanyList(Long accountId) {
        List<CompanyJpa> ownedCompanyJpaList = companyJpaRepository.findBelongedCompanyList(accountId);
        return companyMapper.toBelongedCompanyList(ownedCompanyJpaList);
    }

}
