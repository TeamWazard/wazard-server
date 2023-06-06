package shop.wazard.application.port.out;

import shop.wazard.application.domain.Company;

public interface CompanyPort {

    Company findCompanyById(Long id);

    void saveCompany(String email, Company company);

    void updateCompanyInfo(Company company);
}
