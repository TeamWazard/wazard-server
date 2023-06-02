package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkRecordForWorkerManagement {

    private int tardyCount;
    private int absentCount;
    private int workDayCount;

}
