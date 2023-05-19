package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class AttendanceForWorkerManagement {

    private Long accountId;
    private Long companyId;
    private LocalDate enterDate;
    private LocalDateTime enterTime;
    private LocalDate exitDate;
    private LocalDateTime exitTime;

}
