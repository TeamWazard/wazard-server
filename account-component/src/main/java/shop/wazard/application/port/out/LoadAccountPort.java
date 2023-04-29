package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Account;

import java.util.Optional;

public interface LoadAccountPort {

    void doubleCheckEmail(String email);

    Optional<Account> findAccountByEmail(String email);

    Long findAccountIdByEmail(String email);

    Optional<Account> findAccountByEmailForUserDetails(String email);

}
