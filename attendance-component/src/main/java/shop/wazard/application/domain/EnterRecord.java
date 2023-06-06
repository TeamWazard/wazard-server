package shop.wazard.application.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.RecordEnterTimeReqDto;

@Getter
@Builder
public class EnterRecord {

    private Long accountId;
    private Long companyId;
    private boolean tardy;
    private LocalDate enterDate;
    private LocalDateTime enterTime;

    public static EnterRecord createEnterRecordForAttendance(
            RecordEnterTimeReqDto recordEnterTimeReqDto) {
        return EnterRecord.builder()
                .accountId(recordEnterTimeReqDto.getAccountId())
                .companyId(recordEnterTimeReqDto.getCompanyId())
                .tardy(recordEnterTimeReqDto.isTardy())
                .enterDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .build();
    }
}
