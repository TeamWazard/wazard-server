package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.PermitWorkerToJoinReqDto;

@Getter
@Builder
public class RosterForWorkerManagement {

    private Long companyId;
    private Long accountId;
    RelationType relationType;

    public static RosterForWorkerManagement createRosterForWorkerManagement(PermitWorkerToJoinReqDto permitWorkerToJoinReqDto) {
        return RosterForWorkerManagement.builder()
                .accountId(permitWorkerToJoinReqDto.getAccountId())
                .companyId(permitWorkerToJoinReqDto.getCompanyId())
                .relationType(RelationType.EMPLOYEE)
                .build();
    }

}
