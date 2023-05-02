package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;

@Repository
@RequiredArgsConstructor
class CompanyDbAdapter implements LoadCompanyPort, SaveCompanyPort, UpdateCompanyPort {

    private CompanyMapper companyMapper;
    private CompanyJpaRepository companyJpaRepository;

    @Override
    public Account findAccountByEmail(String email) {
        return null;
    }

    @Override
    public Company saveCompany(Company company) {
        return null;
    }
}
