package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.exception.JoinWorkerDeniedException;
import shop.wazard.util.exception.StatusEnum;

@Getter
@Builder
public class WaitingInfo {

    private Long waitingListId;
    private Long companyId;
    private Long accountId;
    private WaitingStatus waitingStatus;

    public void changeWaitingStatus() {
        if (!isAgreed()) {
            throw new JoinWorkerDeniedException(StatusEnum.JOIN_WORKER_DENIED.getMessage());
        }
        this.waitingStatus = WaitingStatus.JOINED;
    }

    private boolean isAgreed() {
        return this.waitingStatus.equals(WaitingStatus.AGREED);
    }

}
