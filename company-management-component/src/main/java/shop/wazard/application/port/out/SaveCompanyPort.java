package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Company;

public interface SaveCompanyPort {

    void saveCompany(String email, Company company);

}