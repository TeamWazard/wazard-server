package shop.wazard.application.port.in;

import shop.wazard.dto.CommuteRecordReqDto;
import shop.wazard.dto.CommuteRecordResDto;
import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.MarkingAbsentResDto;

public interface AttendanceService {

    MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto);

    CommuteRecordResDto recordCommute(CommuteRecordReqDto commuteRecordReqDto);

}
