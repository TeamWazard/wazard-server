package shop.wazard.application.port.out;

import shop.wazard.application.domain.AccountForManagement;

public interface AccountForCompanyManagementPort {
    AccountForManagement findAccountByEmail(String email);



}
