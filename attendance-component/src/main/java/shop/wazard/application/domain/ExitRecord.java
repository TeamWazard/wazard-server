package shop.wazard.application.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.RecordExitTimeReqDto;

@Getter
@Builder
public class ExitRecord {

    private Long accountId;
    private Long companyId;
    private LocalDateTime exitTime;

    public static ExitRecord createExitRecordForAttendance(
            RecordExitTimeReqDto recordExitTimeReqDto) {
        return ExitRecord.builder()
                .accountId(recordExitTimeReqDto.getAccountId())
                .companyId(recordExitTimeReqDto.getCompanyId())
                .exitTime(LocalDateTime.now())
                .build();
    }
}
