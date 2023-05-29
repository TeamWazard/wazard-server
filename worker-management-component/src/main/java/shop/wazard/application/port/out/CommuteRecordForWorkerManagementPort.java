package shop.wazard.application.port.out;

import shop.wazard.dto.GetWorkerAttendacneRecordReqDto;
import shop.wazard.dto.GetWorkerAttendanceRecordResDto;

import java.time.LocalDate;

public interface CommuteRecordForWorkerManagementPort {

    GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(GetWorkerAttendacneRecordReqDto getWorkerAttendacneRecordReqDto, LocalDate startDate, LocalDate endDate);

}
