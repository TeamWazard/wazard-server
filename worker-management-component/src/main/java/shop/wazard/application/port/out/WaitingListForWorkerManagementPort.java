package shop.wazard.application.port.out;

import shop.wazard.dto.PermitWorkerToJoinReqDto;

public interface WaitingListForWorkerManagementPort {

    void changeWaitingStatus(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto);

}
