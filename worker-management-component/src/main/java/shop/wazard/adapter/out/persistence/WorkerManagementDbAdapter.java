package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.adapter.out.persistence.repository.AccountForWorkerManagementRepository;
import shop.wazard.adapter.out.persistence.repository.RosterForWorkerManagementRepository;
import shop.wazard.adapter.out.persistence.repository.WaitingListForWorkerManagementRepository;
import shop.wazard.application.port.out.WorkerManagementPort;

@Repository
@RequiredArgsConstructor
class WorkerManagementDbAdapter implements WorkerManagementPort {

    private final WorkerManagementMapper workerForManagementMapper;
    private final AccountForWorkerManagementRepository accountForWorkerManagementRepository;
    private final RosterForWorkerManagementRepository rosterForWorkerManagementRepository;
    private final WaitingListForWorkerManagementRepository waitingListForWorkerManagementRepository;

    @Override
    public void updateWorkerWaitingStatus(Long accountId, Long companyId) {

    }
}
