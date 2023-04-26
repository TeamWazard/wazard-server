package shop.wazard.application.port.out;

import shop.wazard.adapter.out.persistence.Account;

import java.util.Optional;

public interface LoadAccountPort {

    void doubleCheckEmail(String email);

    Optional<Account> findAccountByEmail(String email);

    Long findAccountIdByEmail(String email);

}
