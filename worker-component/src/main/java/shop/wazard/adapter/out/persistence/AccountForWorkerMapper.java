package shop.wazard.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.AccountForWorker;
import shop.wazard.entity.account.AccountJpa;

@Component
@Slf4j
class AccountForWorkerMapper {

    public AccountForWorker toAccount(AccountJpa accountJpa) {
        log.info("{}", accountJpa.getRoles());
        return AccountForWorker.builder()
                .id(accountJpa.getId())
                .email(accountJpa.getEmail())
                .userName(accountJpa.getUserName())
                .roles(accountJpa.getRoles())
                .build();
    }

}
