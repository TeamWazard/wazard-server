package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WaitingInfo {

    private Long companyId;
    private Long accountId;
    private WaitingStatus waitingStatus;

}
