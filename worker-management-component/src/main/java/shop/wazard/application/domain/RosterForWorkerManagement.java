package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RosterForWorkerManagement {

    private Long companyId;
    private Long accountId;
    RelationType relationType;

    public static RosterForWorkerManagement createRosterForWorkerManagement(WaitingInfo waitingInfo) {
        return RosterForWorkerManagement.builder()
                .accountId(waitingInfo.getAccountId())
                .companyId(waitingInfo.getCompanyId())
                .relationType(RelationType.EMPLOYEE)
                .build();
    }

}
