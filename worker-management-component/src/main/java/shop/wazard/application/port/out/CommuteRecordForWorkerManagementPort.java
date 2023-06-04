package shop.wazard.application.port.out;

import java.time.LocalDate;
import shop.wazard.dto.GetWorkerAttendanceRecordReqDto;
import shop.wazard.dto.GetWorkerAttendanceRecordResDto;

public interface CommuteRecordForWorkerManagementPort {

    GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(
            GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto,
            LocalDate startDate,
            LocalDate endDate);
}
