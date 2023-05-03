package shop.wazard.application.port.out.account;

import shop.wazard.application.port.domain.Account;

public interface LoadAccountPort {
    Account findAccountByEmail(String email);

}
