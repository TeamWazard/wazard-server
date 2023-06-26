package shop.wazard.application.port.in;

import java.util.List;
import shop.wazard.dto.*;

public interface WorkerManagementService {

    PermitWorkerToJoinResDto permitWorkerToJoin(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto);

    List<WorkerBelongedToCompanyResDto> getWorkersBelongedCompany(
            Long companyId, WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto);

    ExileWorkerResDto exileWorker(ExileWorkerReqDto exileWorkerReqDto);

    List<WaitingWorkerResDto> getWaitingWorkers(
            Long companyId, WaitingWorkerReqDto waitingWorkerReqDto);

    GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(
            GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto, int year, int month);

    List<GetAllReplaceRecordResDto> getAllReplaceRecord(
            GetAllReplaceRecordReqDto getAllReplaceRecordReqDto);

    GetWorkerAttitudeScoreResDto getWorkerAttitudeScore(
            GetWorkerAttitudeScoreReqDto getWorkerAttitudeScoreReqDto);

    RegisterContractInfoResDto registerContractInfo(
            RegisterContractInfoReqDto registerContractInfoReqDto);
}
