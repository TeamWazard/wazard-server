package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.port.domain.Account;
import shop.wazard.entity.account.AccountJpa;

@Component
class AccountMapperForManagement {

    public Account toAccount(AccountJpa accountJpa) {
        return Account.builder()
                .id(accountJpa.getId())
                .userName(accountJpa.getUserName())
                .roles(accountJpa.getRoles())
                .build();
    }

}
