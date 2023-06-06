package shop.wazard.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.AccountForWorkerManagement;
import shop.wazard.entity.account.AccountJpa;

@Component
@Slf4j
public class AccountForWorkerManagementMapper {

    public AccountForWorkerManagement toAccountDomain(AccountJpa accountJpa) {
        return AccountForWorkerManagement.builder()
                .id(accountJpa.getId())
                .roles(accountJpa.getRoles())
                .build();
    }
}
