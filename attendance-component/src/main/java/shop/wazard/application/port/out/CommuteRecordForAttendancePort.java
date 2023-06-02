package shop.wazard.application.port.out;

import java.util.List;
import shop.wazard.application.domain.Attendance;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.application.domain.ExitRecord;
import shop.wazard.dto.GetAttendanceByDayOfTheWeekResDto;
import shop.wazard.dto.RecordExitTimeReqDto;

public interface CommuteRecordForAttendancePort {

    void recordEnterTime(EnterRecord enterRecord);

    List<GetAttendanceByDayOfTheWeekResDto> getMyAttendanceByDayOfTheWeek(Attendance attendance);

    void recordExitTime(ExitRecord exitRecord, Long enterRecordId);

    Long findEnterRecord(RecordExitTimeReqDto recordExitTimeReqDto);

    List<GetAttendanceByDayOfTheWeekResDto> getAttendancesByDayOfTheWeek(Attendance attendance);
}
