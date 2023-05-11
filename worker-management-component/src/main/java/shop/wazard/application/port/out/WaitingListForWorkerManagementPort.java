package shop.wazard.application.port.out;

import shop.wazard.application.domain.WaitingInfo;

public interface WaitingListForWorkerManagementPort {

    WaitingInfo findWaitingInfo(Long waitingListId);

}
