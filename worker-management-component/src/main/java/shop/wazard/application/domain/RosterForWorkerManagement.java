package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RosterForWorkerManagement {

    private Long rosterId;
    private Long companyId;
    private Long accountId;
    RelationType relationType;
    private BaseStatus baseStatus;

    public static RosterForWorkerManagement createRosterForWorkerManagement(WaitingInfo waitingInfo) {
        return RosterForWorkerManagement.builder()
                .accountId(waitingInfo.getAccountId())
                .companyId(waitingInfo.getCompanyId())
                .relationType(RelationType.EMPLOYEE)
                .build();
    }

    public void updateRosterStateForExile() {
        this.baseStatus = BaseStatus.INACTIVE;
    }
}
