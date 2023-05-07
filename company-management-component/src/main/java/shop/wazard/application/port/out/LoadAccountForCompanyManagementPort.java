package shop.wazard.application.port.out;

import shop.wazard.application.domain.AccountForManagement;

public interface LoadAccountForCompanyManagementPort {
    AccountForManagement findAccountByEmail(String email);

}
