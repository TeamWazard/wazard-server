package shop.wazard.application.port.out;

import shop.wazard.application.domain.ContractInfo;

public interface WaitingListForWorkerPort {
    void modifyWaitingListState(ContractInfo contractInfo);
}
