package shop.wazard.application.port.in;

import shop.wazard.dto.*;

import java.time.LocalDate;

public interface AttendanceService {

    MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto);

    RecordEnterTimeResDto recordEnterTime(RecordEnterTimeReqDto recordEnterTimeReqDto);

    GetAttendanceResDto getMyAttendance(GetAttendanceReqDto getAttendanceReqDto, LocalDate date);
}
