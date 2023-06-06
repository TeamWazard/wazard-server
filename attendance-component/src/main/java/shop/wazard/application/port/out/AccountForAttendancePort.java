package shop.wazard.application.port.out;

import shop.wazard.application.domain.AccountForAttendance;

public interface AccountForAttendancePort {

    AccountForAttendance findAccountByEmail(String email);
}
