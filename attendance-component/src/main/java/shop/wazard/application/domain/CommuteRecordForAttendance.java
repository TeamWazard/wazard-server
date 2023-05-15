package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.GoToWorkReqDto;

@Getter
@Builder
public class CommuteRecordForAttendance {

    private Long accountId;
    private Long companyId;
    private boolean tardy;
    private CommuteType commuteType;

    public static CommuteRecordForAttendance goToWorkForAttendance(GoToWorkReqDto goToWorkReqDto) {
        return CommuteRecordForAttendance.builder()
                .accountId(goToWorkReqDto.getAccountId())
                .companyId(goToWorkReqDto.getCompanyId())
                .tardy(goToWorkReqDto.isTardy())
                .commuteType(goToWorkReqDto.getCommuteType())
                .build();
    }

}
