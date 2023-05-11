package shop.wazard.application.port.in;

import shop.wazard.dto.PermitWorkerToJoinReqDto;
import shop.wazard.dto.PermitWorkerToJoinResDto;

public interface WorkerManagementService {

    public PermitWorkerToJoinResDto permitWorkerToJoin(PermitWorkerToJoinReqDto approveWorkerReqDto);

}
