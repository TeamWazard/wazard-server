package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AbsentForAttendance;
import shop.wazard.application.domain.AccountForAttendance;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.application.port.out.AbsentForAttendancePort;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;
import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.MarkingAbsentResDto;
import shop.wazard.dto.RecordEnterTimeReqDto;
import shop.wazard.dto.RecordEnterTimeResDto;

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
    public RecordEnterTimeResDto recordEnterTime(RecordEnterTimeReqDto recordEnterTimeReqDto) {
        EnterRecord enterRecord = EnterRecord.createEnterRecordForAttendance(recordEnterTimeReqDto);
        commuteRecordForAttendancePort.recordEnterTime(enterRecord);
        return RecordEnterTimeResDto.builder()
                .message("출근 시간이 기록되었습니다.")
                .build();
    }
}
