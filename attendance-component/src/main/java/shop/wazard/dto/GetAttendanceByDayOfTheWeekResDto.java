package shop.wazard.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAttendanceByDayOfTheWeekResDto {

    private Long accountId;
    private String userName;
    private LocalDateTime enterTime;
    private LocalDateTime exitTime;
}
