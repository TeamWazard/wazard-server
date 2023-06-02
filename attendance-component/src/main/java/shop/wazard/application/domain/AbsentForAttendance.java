package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.MarkingAbsentReqDto;

@Getter
@Builder
public class AbsentForAttendance {

    private Long accountId;
    private Long companyId;

    public static AbsentForAttendance createAbsentForAttendance(
            MarkingAbsentReqDto markingAbsentReqDto) {
        return AbsentForAttendance.builder()
                .accountId(markingAbsentReqDto.getAccountId())
                .companyId(markingAbsentReqDto.getCompanyId())
                .build();
    }
}
