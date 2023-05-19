package shop.wazard.application.port.out;

import shop.wazard.application.domain.EnterRecord;

public interface CommuteRecordForAttendancePort {

    void recordEnterTime(EnterRecord enterRecord);

}
