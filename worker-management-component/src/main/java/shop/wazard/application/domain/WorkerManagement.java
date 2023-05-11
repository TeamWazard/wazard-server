package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkerManagement {

    private WaitingInfo waitingInfo;

}
