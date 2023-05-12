package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;
import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.MarkingAbsentResDto;

@Transactional
@Service
@RequiredArgsConstructor
class AttendanceServiceImpl implements AttendanceService {

    private final AccountForAttendancePort accountForAttendancePort;
    private final CommuteRecordForAttendancePort commuteRecordForAttendancePort;

    @Override
    public MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto) {
        return null;
    }

}
