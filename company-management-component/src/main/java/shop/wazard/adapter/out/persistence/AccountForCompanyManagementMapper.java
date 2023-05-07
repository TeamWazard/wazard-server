package shop.wazard.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.AccountForManagement;
import shop.wazard.entity.account.AccountJpa;

@Component
@Slf4j
class AccountForCompanyManagementMapper {

    public AccountForManagement toAccount(AccountJpa accountJpa) {
        log.info("{}", accountJpa.getRoles());
        return AccountForManagement.builder()
                .id(accountJpa.getId())
                .userName(accountJpa.getUserName())
                .roles(accountJpa.getRoles())
                .build();
    }

}
