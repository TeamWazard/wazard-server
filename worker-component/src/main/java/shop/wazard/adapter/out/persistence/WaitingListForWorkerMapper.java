package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.ContractInfo;
import shop.wazard.entity.company.WaitingListJpa;
import shop.wazard.entity.company.WaitingStatusJpa;

@Component
class WaitingListForWorkerMapper {

    public void modifyWaitingListState(WaitingListJpa waitingListJpa, ContractInfo contractInfo) {
        if (contractInfo.isContractInfoAgreement()) {
            waitingListJpa.updateWaitingStatus(WaitingStatusJpa.AGREED);
            return;
        }
        waitingListJpa.updateWaitingStatus(WaitingStatusJpa.DISAGREED);
    }
}
