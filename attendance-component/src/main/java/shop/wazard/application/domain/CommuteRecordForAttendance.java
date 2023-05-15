package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.RecordAttendanceReqDto;

@Getter
@Builder
public class CommuteRecordForAttendance {

    private Long accountId;
    private Long companyId;
    private boolean tardy;
    private CommuteType commuteType;

    public static CommuteRecordForAttendance recordAttendanceForAttendance(RecordAttendanceReqDto recordAttendanceReqDto) {
        return CommuteRecordForAttendance.builder()
                .accountId(recordAttendanceReqDto.getAccountId())
                .companyId(recordAttendanceReqDto.getCompanyId())
                .tardy(recordAttendanceReqDto.isTardy())
                .commuteType(recordAttendanceReqDto.getCommuteType())
                .build();
    }

}
