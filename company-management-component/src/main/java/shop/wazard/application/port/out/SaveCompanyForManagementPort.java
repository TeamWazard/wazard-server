package shop.wazard.application.port.out;

import shop.wazard.application.domain.CompanyForManagement;

public interface SaveCompanyForManagementPort {

    void saveCompany(String email, CompanyForManagement companyForManagement);

}
