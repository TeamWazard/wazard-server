package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.port.out.AccountForWorkerManagementPort;
import shop.wazard.application.port.out.RosterForWorkerManagementPort;
import shop.wazard.application.port.out.WaitingListForWorkerManagementPort;
import shop.wazard.application.port.out.WorkerManagementPort;
import shop.wazard.dto.PermitWorkerToJoinReqDto;
import shop.wazard.entity.company.RosterTypeJpa;
import shop.wazard.entity.company.WaitingListJpa;
import shop.wazard.exception.WorkerNotFoundInWaitingListException;
import shop.wazard.util.exception.StatusEnum;

@Repository
@RequiredArgsConstructor
class WorkerManagementDbAdapter implements WorkerManagementPort, AccountForWorkerManagementPort, RosterForWorkerManagementPort, WaitingListForWorkerManagementPort {

    private final WorkerManagementMapper workerForManagementMapper;
    private final AccountForWorkerManagementRepository accountForWorkerManagementRepository;
    private final RosterForWorkerManagementRepository rosterForWorkerManagementRepository;
    private final WaitingListForWorkerManagementRepository waitingListForWorkerManagementRepository;

    @Override
    public void joinWorker(RosterForWorkerManagement rosterForWorkerManagement) {
        rosterForWorkerManagementRepository.save(rosterForWorkerManagement.getAccountJpa(), rosterForWorkerManagement.getCompanyJpa(), RosterTypeJpa.EMPLOYEE);
    }

    @Override
    public void changeWaitingStatus(WaitingInfo waitingInfo) {
        WaitingListJpa waitingListJpa = waitingListForWorkerManagementRepository.findById(waitingInfo.getId())
                .orElseThrow(StatusEnum.WORKER_NOT_FOUND_IN_WAITING_LIST.getMessage());
        waitingListJpa.permitWaitingStatus();
    }

    @Override
    public WaitingInfo findWaitingInfo(Long waitingListId) {
        WaitingListJpa waitingListJpa = WaitingListForWorkerManagementRepository.findById(waitingListId)
                .orElseThrow(() -> new WorkerNotFoundInWaitingListException(StatusEnum.WORKER_NOT_FOUND_IN_WAITING_LIST.getMessage()));
        return workerForManagementMapper.toWaitingInfoDomain(waitingListJpa);
    }
}
