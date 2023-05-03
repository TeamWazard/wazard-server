package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.out.account.LoadAccountPort;
import shop.wazard.application.port.out.company.LoadCompanyPort;
import shop.wazard.application.port.out.company.SaveCompanyPort;
import shop.wazard.application.port.out.company.UpdateCompanyPort;

@Repository
@RequiredArgsConstructor
class CompanyDbAdapter implements LoadCompanyPort, SaveCompanyPort, UpdateCompanyPort, LoadAccountPort {

    private final CompanyMapper companyMapper;
    private final CompanyJpaRepository companyJpaRepository;
    private final LoadAccountJpaRepository loadAccountJpaRepository;
    private final RelationRepository relationRepository;

    @Override
    public Account findAccountByEmail(String email) {
        return null;
    }

    @Override
    public void saveCompany(Company company) {

    }

}
