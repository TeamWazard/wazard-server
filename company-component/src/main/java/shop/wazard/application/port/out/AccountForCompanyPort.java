package shop.wazard.application.port.out;

import shop.wazard.application.domain.AccountForCompany;

public interface AccountForCompanyPort {
    AccountForCompany findAccountByEmail(String email);



}
