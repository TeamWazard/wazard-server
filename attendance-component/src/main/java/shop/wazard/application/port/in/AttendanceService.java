package shop.wazard.application.port.in;

import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.MarkingAbsentResDto;
import shop.wazard.dto.RecordAttendanceReqDto;
import shop.wazard.dto.RecordAttendanceResDto;

public interface AttendanceService {

    MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto);

    RecordAttendanceResDto recordAttendance(RecordAttendanceReqDto recordAttendanceReqDto);

}
