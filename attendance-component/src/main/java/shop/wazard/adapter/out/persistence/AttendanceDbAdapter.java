package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AccountForAttendance;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;

@Repository
@RequiredArgsConstructor
class AttendanceDbAdapter implements AccountForAttendancePort, CommuteRecordForAttendancePort {

    private final AttendanceMapper attendanceMapper;
    private final AccountJpaForAttendanceRepository accountJpaForAttendanceRepository;
    private final CommuteRecordJpaForAttendancePort commuteRecordJpaForAttendancePort;

    @Override
    public AccountForAttendance findAccountByEmail(String email) {
        return null;
    }

}
