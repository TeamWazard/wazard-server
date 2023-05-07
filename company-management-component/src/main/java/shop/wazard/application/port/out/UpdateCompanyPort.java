package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.CompanyForManagement;

public interface UpdateCompanyPort {

    void updateCompanyInfo(CompanyForManagement companyForManagement);

    void deleteCompany(Long companyId);

}
