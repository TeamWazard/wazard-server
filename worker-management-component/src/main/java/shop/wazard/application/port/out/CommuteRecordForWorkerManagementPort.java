package shop.wazard.application.port.out;

import shop.wazard.dto.GetWorkerAttendanceRecordReqDto;
import shop.wazard.dto.GetWorkerAttendanceRecordResDto;

import java.time.LocalDate;

public interface CommuteRecordForWorkerManagementPort {

    GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto, LocalDate startDate, LocalDate endDate);

}
