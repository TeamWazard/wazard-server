package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.AccountForMyPage;
import shop.wazard.entity.account.AccountJpa;

@Component
class AccountForMyPageMapper {

    public AccountForMyPage toAccountForAttendanceDomain(AccountJpa accountJpa) {
        return AccountForMyPage.builder()
                .id(accountJpa.getId())
                .roles(accountJpa.getRoles())
                .build();
    }

}
