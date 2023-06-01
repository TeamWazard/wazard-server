package shop.wazard.application.port.in;

import shop.wazard.dto.*;

import java.util.List;

public interface WorkerManagementService {

    PermitWorkerToJoinResDto permitWorkerToJoin(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto);

    List<WorkerBelongedToCompanyResDto> getWorkersBelongedCompany(WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto);

    ExileWorkerResDto exileWorker(ExileWorkerReqDto exileWorkerReqDto);

    List<WaitingWorkerResDto> getWaitingWorkers(Long companyId, WaitingWorkerReqDto waitingWorkerReqDto);

    GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto, int year, int month);

    List<GetAllReplaceRecordResDto> getAllReplaceRecord(GetAllReplaceRecordReqDto getAllReplaceRecordReqDto);

}