package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class CommuteRecordDto {

    private LocalDate commuteDate;
    private LocalDateTime enterTime;
    private LocalDateTime exitTime;
    private boolean tardy;

}
