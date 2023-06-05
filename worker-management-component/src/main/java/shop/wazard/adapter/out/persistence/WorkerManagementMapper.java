package shop.wazard.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.*;
import shop.wazard.dto.*;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity.BaseStatusJpa;
import shop.wazard.entity.commuteRecord.AbsentJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.RosterJpa;
import shop.wazard.entity.company.WaitingListJpa;
import shop.wazard.entity.company.WaitingStatusJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
class WorkerManagementMapper {

    public WaitingInfo toWaitingInfoDomain(WaitingListJpa waitingListJpa) {
        return WaitingInfo.builder()
                .waitingListId(waitingListJpa.getId())
                .companyId(waitingListJpa.getCompanyJpa().getId())
                .accountId(waitingListJpa.getAccountJpa().getId())
                .waitingStatus(WaitingStatus.valueOf(waitingListJpa.getWaitingStatusJpa().getStatus()))
                .build();
    }

    public void updateWaitingStatus(WaitingListJpa waitingListJpa, WaitingInfo waitingInfo) {
        waitingListJpa.updateWaitingStatus(WaitingStatusJpa.valueOf(waitingInfo.getWaitingStatus().getStatus()));
    }

    public List<WorkerBelongedToCompanyResDto> toWorkersBelongedToCompany(List<AccountJpa> workersBelongedCompany) {
        return workersBelongedCompany.stream()
                .map(accountJpa -> WorkerBelongedToCompanyResDto.builder()
                        .accountId(accountJpa.getId())
                        .userName(accountJpa.getUserName())
                        .birth(accountJpa.getBirth())
                        .genderType(GenderType.valueOf(accountJpa.getGender().getGender()))
                        .build()
                ).collect(Collectors.toList());
    }

    public RosterForWorkerManagement toRosterDomain(RosterJpa rosterJpa) {
        return RosterForWorkerManagement.builder()
                .rosterId(rosterJpa.getId())
                .accountId(rosterJpa.getAccountJpa().getId())
                .companyId(rosterJpa.getCompanyJpa().getId())
                .baseStatus(BaseStatus.valueOf(rosterJpa.getBaseStatusJpa().getStatus()))
                .build();
    }

    public void updateRosterStateForExile(RosterJpa rosterJpa, RosterForWorkerManagement rosterForWorkerManagement) {
        rosterJpa.updateRosterStateForExile(BaseStatusJpa.valueOf(rosterForWorkerManagement.getBaseStatus().getStatus()));
    }

    public List<WaitingWorkerResDto> toWaitingWorkerList(List<WaitingListJpa> waitingWorkerJpaList) {
        return waitingWorkerJpaList.stream()
                .map(waitingListJpa -> WaitingWorkerResDto.builder()
                        .accountId(waitingListJpa.getAccountJpa().getId())
                        .email(waitingListJpa.getAccountJpa().getEmail())
                        .userName(waitingListJpa.getAccountJpa().getUserName())
                        .waitingStatus(WaitingStatus.valueOf(waitingListJpa.getWaitingStatusJpa().getStatus()))
                        .build())
                .collect(Collectors.toList());
    }

    public GetWorkerAttendanceRecordResDto toWorkerAttendaceRecord(AccountJpa accountJpa, List<EnterRecordJpa> enterRecordJpaList, List<AbsentJpa> absentJpaList) {
        List<CommuteRecordDto> commuteRecordDtoList = enterRecordJpaList.stream()
                .map(enterRecordJpa -> CommuteRecordDto.builder()
                        .commuteDate(enterRecordJpa.getEnterDate())
                        .enterTime(enterRecordJpa.getEnterTime())
                        .exitTime(enterRecordJpa.getExitRecordJpa().getExitTime())
                        .tardy(enterRecordJpa.isTardy())
                        .build())
                .collect(Collectors.toList());
        List<AbsentRecordDto> absentRecordDtoList = absentJpaList.stream()
                .map(absentJpa -> AbsentRecordDto.builder()
                        .absentDate(absentJpa.getAbsentDate())
                        .build())
                .collect(Collectors.toList());
        return GetWorkerAttendanceRecordResDto.builder()
                .userName(accountJpa.getUserName())
                .commuteRecordResDtoList(commuteRecordDtoList)
                .absentRecordResDtoList(absentRecordDtoList)
                .build();
    }

    public List<GetAllReplaceRecordResDto> toAllReplaceRecord(List<ReplaceWorkerJpa> replaceWorkerJpaList) {
        return replaceWorkerJpaList.stream()
                .map(ReplaceWorkerJpa -> GetAllReplaceRecordResDto.builder()
                        .userName(ReplaceWorkerJpa.getAccountJpa().getUserName())
                        .replaceWorkerName(ReplaceWorkerJpa.getReplaceWorkerName())
                        .replaceDate(ReplaceWorkerJpa.getReplaceDate())
                        .enterTime(ReplaceWorkerJpa.getEnterTime())
                        .exitTime(ReplaceWorkerJpa.getExitTime())
                        .build()
                ).collect(Collectors.toList());
    }

}