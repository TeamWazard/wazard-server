package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.exception.JoinWorkerDeniedException;
import shop.wazard.util.exception.StatusEnum;

@Getter
@Builder
public class WaitingInfo {

    private Long companyId;
    private Long accountId;
    private WaitingStatus waitingStatus;

    public void changeWaitingStatus() {
        if (isAgreed()) {
            this.waitingStatus = WaitingStatus.JOINED;
        }
    }

    private boolean isAgreed() {
        if (!this.waitingStatus.equals(WaitingStatus.AGREED)) {
            throw new JoinWorkerDeniedException(StatusEnum.IS_NOT_AGREED_WORKER.getMessage());
        }
        return false;
    }

}
