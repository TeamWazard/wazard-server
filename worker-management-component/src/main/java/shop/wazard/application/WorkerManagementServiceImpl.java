package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForWorkerManagement;
import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.application.port.out.AccountForWorkerManagementPort;
import shop.wazard.application.port.out.CommuteRecordForWorkerManagementPort;
import shop.wazard.application.port.out.RosterForWorkerManagementPort;
import shop.wazard.application.port.out.WaitingListForWorkerManagementPort;
import shop.wazard.dto.*;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
class WorkerManagementServiceImpl implements WorkerManagementService {

    private final AccountForWorkerManagementPort accountForWorkerManagementPort;
    private final RosterForWorkerManagementPort rosterForWorkerManagementPort;
    private final WaitingListForWorkerManagementPort waitingListForWorkerManagementPort;
    private final CommuteRecordForWorkerManagementPort commuteRecordForWorkerManagementPort;

    @Override
    public PermitWorkerToJoinResDto permitWorkerToJoin(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto) {
        AccountForWorkerManagement accountForWorkerManagement = accountForWorkerManagementPort.findAccountByEmail(permitWorkerToJoinReqDto.getEmail());
        accountForWorkerManagement.checkIsAuthorizedAccount();
        WaitingInfo waitingInfo = waitingListForWorkerManagementPort.findWaitingInfo(permitWorkerToJoinReqDto.getWaitingListId());
        waitingInfo.changeWaitingStatus();
        waitingListForWorkerManagementPort.updateWaitingStatus(waitingInfo);
        rosterForWorkerManagementPort.joinWorker(RosterForWorkerManagement.createRosterForWorkerManagement(waitingInfo));
        return PermitWorkerToJoinResDto.builder()
                .message("업장 근무자 명단에 추가되었습니다.")
                .build();
    }

    @Override
    public List<WorkerBelongedToCompanyResDto> getWorkersBelongedCompany(WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto) {
        AccountForWorkerManagement accountForWorkerManagement = accountForWorkerManagementPort.findAccountByEmail(workerBelongedToCompanyReqDto.getEmail());
        accountForWorkerManagement.checkIsAuthorizedAccount();
        return rosterForWorkerManagementPort.getWorkersBelongedToCompany(workerBelongedToCompanyReqDto.getCompanyId());
    }

    @Override
    public UpdateAbsentResDto markingAbsent(UpdateAbsentReqDto updateAbsentReqDto) {
        return null;
    }
}
