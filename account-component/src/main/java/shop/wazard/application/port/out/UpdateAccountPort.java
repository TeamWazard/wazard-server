package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Account;

public interface UpdateAccountPort {

    void updateMyProfile(Account account);

}
