package shop.wazard.application.port.out;

import shop.wazard.application.domain.EnterRecord;
import shop.wazard.application.domain.ExitRecord;
import shop.wazard.dto.RecordExitTimeReqDto;

public interface CommuteRecordForAttendancePort {

    void recordEnterTime(EnterRecord enterRecord);

    void recordExitTime(ExitRecord exitRecord);

    boolean findEnterRecord(RecordExitTimeReqDto recordExitTimeReqDto);

}
