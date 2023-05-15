package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AbsentForAttendance;
import shop.wazard.application.domain.AccountForAttendance;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.application.port.out.AbsentForAttendancePort;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;
import shop.wazard.dto.CommuteRecordReqDto;
import shop.wazard.dto.CommuteRecordResDto;
import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.MarkingAbsentResDto;

@Transactional
@Service
@RequiredArgsConstructor
class AttendanceServiceImpl implements AttendanceService {

    private final AccountForAttendancePort accountForAttendancePort;
    private final CommuteRecordForAttendancePort commuteRecordForAttendancePort;
    private final AbsentForAttendancePort absentForAttendancePort;

    @Override
    public MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto) {
        AccountForAttendance accountForAttendance = accountForAttendancePort.findAccountByEmail(markingAbsentReqDto.getEmail());
        accountForAttendance.checkIsEmployer();
        absentForAttendancePort.markingAbsent(AbsentForAttendance.createAbsentForAttendance(markingAbsentReqDto));
        return MarkingAbsentResDto.builder()
                .message("결석 처리가 되었습니다.")
                .build();
    }

    @Override
    public CommuteRecordResDto recordCommute(CommuteRecordReqDto commuteRecordReqDto) {
        return null;
    }
}
