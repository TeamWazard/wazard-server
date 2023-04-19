package shop.wazard.application.port.out;

import shop.wazard.domain.AccountDomain;

import java.util.Optional;

public interface LoadAccountPort {

    void doubleCheckEmail(String email);

    Optional<AccountDomain> findAccountByEmail(String email);

    Long findAccountIdByEmail(String email);

}
