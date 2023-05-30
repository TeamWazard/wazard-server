package shop.wazard.application.port.out;

import shop.wazard.application.domain.Account;

import java.util.Optional;

public interface AccountPort {

    Boolean isPossibleEmail(String email);

    Account findAccountByEmail(String email);

    Long findAccountIdByEmail(String email);

    Optional<Account> findAccountByEmailForUserDetails(String email);

    void save(Account account);

    void updateMyProfile(Account account);

}
