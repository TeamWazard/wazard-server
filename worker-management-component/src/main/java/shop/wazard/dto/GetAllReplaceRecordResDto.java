package shop.wazard.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAllReplaceRecordResDto {

    private String userName;

    private String replaceWorkerName;

    private LocalDate replaceDate;

    private LocalDateTime enterTime;

    private LocalDateTime exitTime;
}
