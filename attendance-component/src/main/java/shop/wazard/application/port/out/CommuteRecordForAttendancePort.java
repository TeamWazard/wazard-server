package shop.wazard.application.port.out;

import shop.wazard.dto.RecordAttendanceReqDto;

public interface CommuteRecordForAttendancePort {

    void recordAttendance(RecordAttendanceReqDto recordAttendanceReqDto);

}
