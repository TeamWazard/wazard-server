package shop.wazard.application.port.out;

import java.util.List;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.dto.WaitingWorkerResDto;

public interface WaitingListForWorkerManagementPort {

    WaitingInfo findWaitingInfo(Long waitingListId);

    void updateWaitingStatus(WaitingInfo waitingInfo);

    List<WaitingWorkerResDto> getWaitingWorker(Long companyId);

    void addWaitingInfo(Long accountId, Long companyId);
}
