package shop.wazard.application.port.out;

import shop.wazard.application.domain.CommuteRecordForAttendance;

public interface CommuteRecordForAttendancePort {

    void goToWork(CommuteRecordForAttendance commuteRecordForAttendance);

}
