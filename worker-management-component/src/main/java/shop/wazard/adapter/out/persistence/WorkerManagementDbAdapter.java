package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.out.WorkerManagementPort;

@Repository
@RequiredArgsConstructor
class WorkerManagementDbAdapter implements WorkerManagementPort {

    private final WorkerManagementMapper workerForManagementMapper;

    @Override
    public void updateWorkerWaitingStatus(Long accountId, Long companyId) {

    }
}