package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.RecordEnterTimeReqDto;

import java.time.LocalDateTime;

@Getter
@Builder
public class EnterRecord {

    private Long accountId;
    private Long companyId;
    private boolean tardy;
    private LocalDateTime enterTime;

    public static EnterRecord createEnterRecordForAttendance(RecordEnterTimeReqDto recordEnterTimeReqDto) {
        return EnterRecord.builder()
                .accountId(recordEnterTimeReqDto.getAccountId())
                .companyId(recordEnterTimeReqDto.getCompanyId())
                .tardy(recordEnterTimeReqDto.isTardy())
                .enterTime(LocalDateTime.now())
                .build();
    }

}
