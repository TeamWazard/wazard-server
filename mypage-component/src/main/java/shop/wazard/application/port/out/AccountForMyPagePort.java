package shop.wazard.application.port.out;

import shop.wazard.application.domain.AccountForMyPage;

public interface AccountForMyPagePort {


    AccountForMyPage findAccountByEmail(String email);

}
