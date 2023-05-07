package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Company;

public interface UpdateCompanyPort {

    void updateCompanyInfo(Company company);

    void deleteCompany(Long companyId);
}
