package shop.wazard.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.*;
import shop.wazard.dto.WorkerBelongedToCompanyResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity.BaseStatusJpa;
import shop.wazard.entity.company.RosterJpa;
import shop.wazard.entity.company.WaitingListJpa;
import shop.wazard.entity.company.WaitingStatusJpa;

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
}