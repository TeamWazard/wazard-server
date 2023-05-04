package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Account;

public interface LoadAccountForCompanyManagementPort {
    Account findAccountByEmail(String email);

}
