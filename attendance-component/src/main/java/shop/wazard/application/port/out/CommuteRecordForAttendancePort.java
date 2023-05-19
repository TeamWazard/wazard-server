package shop.wazard.application.port.out;

import shop.wazard.application.domain.EnterRecord;
import shop.wazard.application.domain.ExitRecord;

public interface CommuteRecordForAttendancePort {

    void recordEnterTime(EnterRecord enterRecord);

    void recordExitTime(ExitRecord exitRecord);

}
