package shop.wazard.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.domain.WaitingStatus;
import shop.wazard.entity.company.WaitingListJpa;
import shop.wazard.entity.company.WaitingStatusJpa;

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
}
