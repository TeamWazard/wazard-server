package shop.wazard.application.port.out.company;

import shop.wazard.application.port.domain.Account;

public interface LoadCompanyPort {

    Account findAccountByEmail(String email);

}
