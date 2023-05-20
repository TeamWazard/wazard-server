package shop.wazard.application.port.out;

import shop.wazard.application.domain.Attendance;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.application.domain.ExitRecord;
import shop.wazard.dto.RecordExitTimeReqDto;
import shop.wazard.dto.GetAttendanceResDto;

import java.util.List;

public interface CommuteRecordForAttendancePort {

    void recordEnterTime(EnterRecord enterRecord);

    void recordExitTime(ExitRecord exitRecord, Long enterRecordId);

    Long findEnterRecord(RecordExitTimeReqDto recordExitTimeReqDto);

    List<GetAttendanceResDto> getAttendancesByDayOfTheWeek(Attendance build);

}
