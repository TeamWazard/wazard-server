package shop.wazard.application.port.in;

import shop.wazard.dto.*;

public interface AttendanceService {

    MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto);

    RecordEnterTimeResDto recordEnterTime(RecordEnterTimeReqDto recordEnterTimeReqDto);

    RecordExitTimeResDto recordExitTime(RecordExitTimeReqDto recordExitTimeReqDto);

}
