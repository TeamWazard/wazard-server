package shop.wazard.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommuteRecordDto {

    private LocalDate commuteDate;
    private LocalDateTime enterTime;
    private LocalDateTime exitTime;
    private boolean tardy;
}
