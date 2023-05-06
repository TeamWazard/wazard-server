package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.AccountForManagement;

public interface LoadAccountForCompanyManagementPort {
    AccountForManagement findAccountByEmail(String email);

}
