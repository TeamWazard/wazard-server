package shop.wazard.application.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkRecordForMyPage {

    private int tardyCount;
    private int absentCount;
    private int workDayCount;
    private LocalDate startWorkingDate;
    private LocalDate endWorkingDate;
}
