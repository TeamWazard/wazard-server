package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetAttendanceByDayOfTheWeekResDto {

    private Long accountId;
    private String userName;
    private LocalDateTime enterTime;
    private LocalDateTime exitTime;

}