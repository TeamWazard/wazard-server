package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForWorkerManagement;
import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.application.port.out.AccountForWorkerManagementPort;
import shop.wazard.application.port.out.RosterForWorkerManagementPort;
import shop.wazard.application.port.out.WaitingListForWorkerManagementPort;
import shop.wazard.application.port.out.WorkerManagementPort;
import shop.wazard.dto.PermitWorkerToJoinReqDto;
import shop.wazard.dto.PermitWorkerToJoinResDto;
import shop.wazard.exception.NotAuthorizedException;
import shop.wazard.util.exception.StatusEnum;

@Transactional
@Service
@RequiredArgsConstructor
class WorkerManagementServiceImpl implements WorkerManagementService {

    private final WorkerManagementPort workerManagementPort;
    private final AccountForWorkerManagementPort accountForWorkerManagementPort;
    private final RosterForWorkerManagementPort rosterForWorkerManagementPort;
    private final WaitingListForWorkerManagementPort waitingListForWorkerManagementPort;

    @Override
    public PermitWorkerToJoinResDto permitWorkerToJoin(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto) {
        AccountForWorkerManagement accountForWorkerManagement = accountForWorkerManagementPort.findAccountByEmail(permitWorkerToJoinReqDto.getEmail());
        if (!accountForWorkerManagement.isEmployer()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
        WaitingInfo waitingInfo = waitingListForWorkerManagementPort.findWaitingInfo(permitWorkerToJoinReqDto);
        waitingInfo.changeWaitingStatus();
        rosterForWorkerManagementPort.joinWorker(RosterForWorkerManagement.createRosterForWorkerManagement(waitingInfo));
        return PermitWorkerToJoinResDto.builder()
                .message("초대가 승인되었습니다.")
                .build();
    }

}
