package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;

public interface LoadCompanyPort {

    Account findAccountByEmail(String email);
    Company saveCompany(Company company);

}
