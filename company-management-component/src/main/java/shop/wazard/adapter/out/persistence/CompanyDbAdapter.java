package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
class CompanyDbAdapter implements LoadCompanyPort, SaveCompanyPort, UpdateCompanyPort {

    private CompanyMapper companyMapper;
    private CompanyJpaRepository companyJpaRepository;
    private EntityManager em;

    @Override
    public Account findAccountByEmail(String email) {
        final AccountJpa accountJpa = em.createQuery("select aj from AccountJpa aj where aj.email = :email", AccountJpa.class)
                .setParameter("email", email)
                .getSingleResult();
        return companyMapper.toAccountDomain(accountJpa);
    }

    @Override
    public void saveCompany(Company company, Account account) {
        final AccountJpa accountJpa = companyMapper.toAccountEntity(account);
        final CompanyJpa companyJpa = companyJpaRepository.save( companyMapper.toCompanyEntity(company));

        final CompanyAccountRelJpa companyAccountRelJpa = CompanyAccountRelJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .build();
        em.persist(companyAccountRelJpa);
    }

}
