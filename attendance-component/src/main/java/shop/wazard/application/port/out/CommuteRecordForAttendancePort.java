package shop.wazard.application.port.out;

import shop.wazard.application.domain.Attendance;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.dto.GetAttendanceResDto;

import java.util.List;

public interface CommuteRecordForAttendancePort {

    void recordEnterTime(EnterRecord enterRecord);

    List<GetAttendanceResDto> getAttendancesByDayOfTheWeek(Attendance build);

}
