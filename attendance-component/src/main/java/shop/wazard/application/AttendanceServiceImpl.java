package shop.wazard.application;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.*;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.application.port.out.AbsentForAttendancePort;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;
import shop.wazard.dto.*;

@Transactional
@Service
@RequiredArgsConstructor
class AttendanceServiceImpl implements AttendanceService {

    private final AccountForAttendancePort accountForAttendancePort;
    private final CommuteRecordForAttendancePort commuteRecordForAttendancePort;
    private final AbsentForAttendancePort absentForAttendancePort;

    @Override
    public MarkingAbsentResDto markingAbsent(MarkingAbsentReqDto markingAbsentReqDto) {
        AccountForAttendance accountForAttendance =
                accountForAttendancePort.findAccountByEmail(markingAbsentReqDto.getEmail());
        accountForAttendance.checkIsEmployer();
        absentForAttendancePort.markingAbsent(
                AbsentForAttendance.createAbsentForAttendance(markingAbsentReqDto));
        return MarkingAbsentResDto.builder().message("결석 처리가 되었습니다.").build();
    }

    @Override
    public RecordEnterTimeResDto recordEnterTime(RecordEnterTimeReqDto recordEnterTimeReqDto) {
        EnterRecord enterRecord = EnterRecord.createEnterRecordForAttendance(recordEnterTimeReqDto);
        commuteRecordForAttendancePort.recordEnterTime(enterRecord);
        return RecordEnterTimeResDto.builder().message("출근 시간이 기록되었습니다.").build();
    }

    @Override
    public RecordExitTimeResDto recordExitTime(RecordExitTimeReqDto recordExitTimeReqDto) {
        ExitRecord exitRecord = ExitRecord.createExitRecordForAttendance(recordExitTimeReqDto);
        Long enterRecordId = commuteRecordForAttendancePort.findEnterRecord(recordExitTimeReqDto);
        commuteRecordForAttendancePort.recordExitTime(exitRecord, enterRecordId);
        return RecordExitTimeResDto.builder().message("퇴근 시간이 기록되었습니다.").build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetAttendanceByDayOfTheWeekResDto> getAttendancesByDayOfTheWeek(
            GetAttendanceByDayOfTheWeekReqDto getAttendanceByDayOfTheWeekReqDto,
            Long companyId,
            LocalDate date) {
        AccountForAttendance accountForAttendance =
                accountForAttendancePort.findAccountByEmail(
                        getAttendanceByDayOfTheWeekReqDto.getEmail());
        accountForAttendance.checkIsEmployer();
        return commuteRecordForAttendancePort.getAttendancesByDayOfTheWeek(
                Attendance.createAttendance(getAttendanceByDayOfTheWeekReqDto, companyId, date));
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetAttendanceByDayOfTheWeekResDto> getMyAttendanceByDayOfTheWeek(
            GetAttendanceByDayOfTheWeekReqDto getAttendanceByDayOfTheWeekReqDto,
            Long companyId,
            LocalDate date) {
        AccountForAttendance accountForAttendance =
                accountForAttendancePort.findAccountByEmail(
                        getAttendanceByDayOfTheWeekReqDto.getEmail());
        accountForAttendance.checkIsEmployee();
        return commuteRecordForAttendancePort.getMyAttendanceByDayOfTheWeek(
                Attendance.createAttendance(getAttendanceByDayOfTheWeekReqDto, companyId, date));
    }
}
