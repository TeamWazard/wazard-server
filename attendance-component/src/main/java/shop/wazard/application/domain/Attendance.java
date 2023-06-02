package shop.wazard.application.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.GetAttendanceByDayOfTheWeekReqDto;

@Getter
@Builder
public class Attendance {

    private Long accountId;
    private Long companyId;
    private LocalDate enterDate;
    private LocalDateTime enterTime;
    private LocalDate exitDate;
    private LocalDateTime exitTime;

    public static Attendance createAttendance(
            GetAttendanceByDayOfTheWeekReqDto getAttendanceByDayOfTheWeekReqDto,
            Long companyId,
            LocalDate date) {
        return Attendance.builder()
                .accountId(getAttendanceByDayOfTheWeekReqDto.getAccountId())
                .companyId(companyId)
                .enterDate(date)
                .build();
    }
}
