package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Account;

import java.util.Optional;

public interface LoadAccountPort {

    Boolean isPossibleEmail(String email);

    Account findAccountByEmail(String email);

    Long findAccountIdByEmail(String email);

    Optional<Account> findAccountByEmailForUserDetails(String email);

}
