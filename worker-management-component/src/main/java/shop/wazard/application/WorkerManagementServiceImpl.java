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

import java.time.LocalDate;
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
        accountForWorkerManagement.checkIsEmployer();
        WaitingInfo waitingInfo = waitingListForWorkerManagementPort.findWaitingInfo(permitWorkerToJoinReqDto.getWaitingListId());
        waitingInfo.changeWaitingStatus();
        waitingListForWorkerManagementPort.updateWaitingStatus(waitingInfo);
        rosterForWorkerManagementPort.joinWorker(RosterForWorkerManagement.createRosterForWorkerManagement(waitingInfo));
        return PermitWorkerToJoinResDto.builder()
                .message("업장 근무자 명단에 추가되었습니다.")
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkerBelongedToCompanyResDto> getWorkersBelongedCompany(WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto) {
        AccountForWorkerManagement accountForWorkerManagement = accountForWorkerManagementPort.findAccountByEmail(workerBelongedToCompanyReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        return rosterForWorkerManagementPort.getWorkersBelongedToCompany(workerBelongedToCompanyReqDto.getCompanyId());
    }

    @Override
    public ExileWorkerResDto exileWorker(ExileWorkerReqDto exileWorkerReqDto) {
        AccountForWorkerManagement accountForWorkerManagement = accountForWorkerManagementPort.findAccountByEmail(exileWorkerReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        RosterForWorkerManagement rosterForWorkerManagement = rosterForWorkerManagementPort.findRoster(exileWorkerReqDto.getAccountId(), exileWorkerReqDto.getCompanyId());
        rosterForWorkerManagement.updateRosterStateForExile();
        rosterForWorkerManagementPort.exileWorker(rosterForWorkerManagement);
        return ExileWorkerResDto.builder()
                .message("해당 근무자가 업장에서 퇴장되었습니다.")
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<WaitingWorkerResDto> getWaitingWorkers(WaitingWorkerReqDto waitingWorkerReqDto) {
        AccountForWorkerManagement accountForWorkerManagement = accountForWorkerManagementPort.findAccountByEmail(waitingWorkerReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        return waitingListForWorkerManagementPort.getWaitingWorker(waitingWorkerReqDto.getCompanyId());
    }

    @Transactional(readOnly = true)
    @Override
    public GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(GetWorkerAttendacneRecordReqDto getWorkerAttendacneRecordReqDto, LocalDate date) {
        AccountForWorkerManagement accountForWorkerManagement = accountForWorkerManagementPort.findAccountByEmail(getWorkerAttendacneRecordReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        return commuteRecordForWorkerManagementPort.getWorkerAttendanceRecord(getWorkerAttendacneRecordReqDto, date);
    }

}
