package shop.wazard.application.port.out;

import shop.wazard.application.domain.AbsentForAttendance;

public interface AbsentForAttendancePort {

    void markingAbsent(AbsentForAttendance absentForAttendance);
}
