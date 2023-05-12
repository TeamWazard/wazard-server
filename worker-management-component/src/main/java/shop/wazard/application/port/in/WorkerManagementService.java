package shop.wazard.application.port.in;

import shop.wazard.dto.PermitWorkerToJoinReqDto;
import shop.wazard.dto.PermitWorkerToJoinResDto;
import shop.wazard.dto.WorkerBelongedToCompanyReqDto;
import shop.wazard.dto.WorkerBelongedToCompanyResDto;

import java.util.List;

public interface WorkerManagementService {

    PermitWorkerToJoinResDto permitWorkerToJoin(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto);

    List<WorkerBelongedToCompanyResDto> getWorkersBelongedCompany(WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto);

}
