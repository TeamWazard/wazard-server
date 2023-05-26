package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class WorkRecordForMyPage {

    private int tardyCount;
    private int absentCount;
    private int workDayCount;
    private LocalDate startWorkDate;
    private LocalDate endWorkDate;

}
