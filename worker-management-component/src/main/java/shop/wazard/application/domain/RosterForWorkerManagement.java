package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.PermitWorkerToJoinReqDto;

@Getter
@Builder
public class RosterForWorkerManagement {

    private Long companyId;
    private Long accountId;
    WaitingStatus waitingStatus;

    public static RosterForWorkerManagement createRosterForWorkerManagement(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto) {
        return RosterForWorkerManagement.builder()
                .accountId(permitWorkerToJoinReqDto.getAccountId())
                .companyId(permitWorkerToJoinReqDto.getCompanyId())
                .waitingStatus(WaitingStatus.JOINED)
                .build();
    }

}
