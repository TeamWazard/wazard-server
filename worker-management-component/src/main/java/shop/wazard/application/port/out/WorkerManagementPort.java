package shop.wazard.application.port.out;

import shop.wazard.application.domain.WaitingInfo;

public interface WorkerManagementPort {

    void changeWaitingStatus(WaitingInfo waitingInfo);

}
