package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;

public interface SaveCompanyAccountRelPort {

    void saveCompanyAccountRel(Company company, Account account);

}
