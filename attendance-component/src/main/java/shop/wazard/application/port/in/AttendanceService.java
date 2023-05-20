package shop.wazard.application.port.in;

import shop.wazard.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto);

    RecordEnterTimeResDto recordEnterTime(RecordEnterTimeReqDto recordEnterTimeReqDto);

    RecordExitTimeResDto recordExitTime(RecordExitTimeReqDto recordExitTimeReqDto);

    List<GetAttendanceResDto> getAttendancesByDayOfTheWeek(GetAttendanceReqDto getAttendanceReqDto, LocalDate date);

}
