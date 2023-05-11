package shop.wazard.application.port.in;

import shop.wazard.dto.ApproveWorkerReqDto;
import shop.wazard.dto.ApproveWorkerResDto;

public interface WorkerManagementService {

    public ApproveWorkerResDto permitWorkerToJoin(ApproveWorkerReqDto approveWorkerReqDto);

}
