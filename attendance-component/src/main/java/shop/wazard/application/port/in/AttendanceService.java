package shop.wazard.application.port.in;

import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.MarkingAbsentResDto;
import shop.wazard.dto.RecordEnterTimeReqDto;
import shop.wazard.dto.RecordEnterTimeResDto;

public interface AttendanceService {

    MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto);

    RecordEnterTimeResDto recordEnterTime(RecordEnterTimeReqDto recordEnterTimeReqDto);

}
