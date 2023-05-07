package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.CompanyForManagement;

public interface SaveCompanyPort {

    void saveCompany(String email, CompanyForManagement companyForManagement);

}
