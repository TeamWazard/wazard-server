package shop.wazard.application.port.in;

import java.time.LocalDate;
import java.util.List;
import shop.wazard.dto.*;

public interface AttendanceService {

    MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto);

    RecordEnterTimeResDto recordEnterTime(RecordEnterTimeReqDto recordEnterTimeReqDto);

    RecordExitTimeResDto recordExitTime(RecordExitTimeReqDto recordExitTimeReqDto);

    List<GetAttendanceByDayOfTheWeekResDto> getAttendancesByDayOfTheWeek(
            GetAttendanceByDayOfTheWeekReqDto getAttendanceByDayOfTheWeekReqDto,
            Long companyId,
            LocalDate date);

    List<GetAttendanceByDayOfTheWeekResDto> getMyAttendanceByDayOfTheWeek(
            GetAttendanceByDayOfTheWeekReqDto getAttendanceByDayOfTheWeekReqDto,
            Long companyId,
            LocalDate date);
}
