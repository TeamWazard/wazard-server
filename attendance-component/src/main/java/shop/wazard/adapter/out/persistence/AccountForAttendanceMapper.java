package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.AccountForAttendance;
import shop.wazard.entity.account.AccountJpa;

@Component
class AccountForAttendanceMapper {

    public AccountForAttendance toAccountForAttendanceDomain(AccountJpa accountJpa) {
        return AccountForAttendance.builder()
                .id(accountJpa.getId())
                .roles(accountJpa.getRoles())
                .build();
    }

}
