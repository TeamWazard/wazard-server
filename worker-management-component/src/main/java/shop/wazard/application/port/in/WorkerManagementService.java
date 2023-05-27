package shop.wazard.application.port.in;

import shop.wazard.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface WorkerManagementService {

    PermitWorkerToJoinResDto permitWorkerToJoin(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto);

    List<WorkerBelongedToCompanyResDto> getWorkersBelongedCompany(WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto);

    ExileWorkerResDto exileWorker(ExileWorkerReqDto exileWorkerReqDto);

    List<WaitingWorkerResDto> getWaitingWorkers(WaitingWorkerReqDto waitingWorkerReqDto);

    GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(GetWorkerAttendacneRecordReqDto getWorkerAttendacneRecordReqDto, LocalDate date);

}
