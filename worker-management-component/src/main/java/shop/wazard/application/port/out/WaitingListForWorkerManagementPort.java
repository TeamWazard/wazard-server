package shop.wazard.application.port.out;

import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.dto.WaitingWorkerResDto;

import java.util.List;

public interface WaitingListForWorkerManagementPort {

    WaitingInfo findWaitingInfo(Long waitingListId);

    void updateWaitingStatus(WaitingInfo waitingInfo);

    List<WaitingWorkerResDto> getWaitingWorker(Long companyId);

}
