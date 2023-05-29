package shop.wazard.application.port.out;

import shop.wazard.dto.GetWorkerAttendacneRecordReqDto;
import shop.wazard.dto.GetWorkerAttendanceRecordResDto;

public interface CommuteRecordForWorkerManagementPort {

    GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(GetWorkerAttendacneRecordReqDto getWorkerAttendacneRecordReqDto, int year, int month);

}
