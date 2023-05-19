package shop.wazard.application.port.out;

import shop.wazard.application.domain.Attendance;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.dto.GetAttendanceResDto;

public interface CommuteRecordForAttendancePort {

    void recordEnterTime(EnterRecord enterRecord);

    GetAttendanceResDto getMyAttendance(Attendance attendance);

}
