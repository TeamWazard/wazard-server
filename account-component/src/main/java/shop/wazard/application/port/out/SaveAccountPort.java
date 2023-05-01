package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Account;

public interface SaveAccountPort {

    void save(Account account);

}
