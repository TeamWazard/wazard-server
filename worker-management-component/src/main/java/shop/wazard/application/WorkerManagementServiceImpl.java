package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.application.port.out.WorkerManagementPort;
import shop.wazard.dto.ApproveWorkerReqDto;
import shop.wazard.dto.ApproveWorkerResDto;

@Transactional
@Service
@RequiredArgsConstructor
class WorkerManagementServiceImpl implements WorkerManagementService {

    private final WorkerManagementPort workerManagementPort;

    @Override
    public ApproveWorkerResDto permitWorkerToJoin(ApproveWorkerReqDto approveWorkerReqDto) {
        return null;
    }

}
