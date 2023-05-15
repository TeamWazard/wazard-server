package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.CommuteRecordReqDto;
import shop.wazard.exception.InvalidTardyStateException;
import shop.wazard.util.exception.StatusEnum;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommuteRecordForAttendance {

    private Long accountId;
    private Long companyId;
    private boolean tardy;
    private CommuteType commuteType;
    private LocalDateTime commuteTime;

    public static CommuteRecordForAttendance createCommuteRecordForAttendance(CommuteRecordReqDto commuteRecordReqDto) {
        return CommuteRecordForAttendance.builder()
                .accountId(commuteRecordReqDto.getAccountId())
                .companyId(commuteRecordReqDto.getCompanyId())
                .tardy(commuteRecordReqDto.isTardy())
                .commuteType(commuteRecordReqDto.getCommuteType())
                .commuteTime(LocalDateTime.now())
                .build();
    }

    public void checkTardyState() {
        if (commuteType.equals(CommuteType.OFF) && tardy) {
            throw new InvalidTardyStateException(StatusEnum.INVALID_TARDY_STATE.getMessage());
        }
    }

}