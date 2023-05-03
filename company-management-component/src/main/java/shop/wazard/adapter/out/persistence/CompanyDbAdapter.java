package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyAccountRelPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
class CompanyDbAdapter implements LoadCompanyPort, SaveCompanyPort, SaveCompanyAccountRelPort, UpdateCompanyPort {

    private CompanyMapper companyMapper;
    private CompanyJpaRepository companyJpaRepository;
    private CompanyAccountRelJpaRepository companyAccountRelJpaRepository;
    private EntityManager em;

    @Override
    public Account findAccountByEmail(String email) {
        final AccountJpa accountJpa = em.createQuery("select aj from AccountJpa aj where aj.email = :email", AccountJpa.class)
                .setParameter("email", email)
                .getSingleResult();
        return companyMapper.toAccountDomain(accountJpa);
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
