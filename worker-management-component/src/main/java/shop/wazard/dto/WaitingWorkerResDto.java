package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.application.domain.WaitingStatus;

@Getter
@Builder
public class WaitingWorkerResDto {

    private Long accountId;
    private String email;
    private String userName;
    private WaitingStatus waitingStatus;
}
