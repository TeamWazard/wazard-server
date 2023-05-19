package shop.wazard.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.domain.AccountForAttendance;
import shop.wazard.application.domain.ExitRecord;
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.application.port.out.AbsentForAttendancePort;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;
import shop.wazard.dto.MarkingAbsentReqDto;
import shop.wazard.dto.RecordEnterTimeReqDto;
import shop.wazard.dto.RecordExitTimeReqDto;
import shop.wazard.exception.EnterRecordNotFoundException;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AttendanceServiceImpl.class})
class AttendanceServiceImplTest {

    @Autowired
    private AttendanceService attendanceService;
    @MockBean
    private AccountForAttendancePort accountForAttendancePort;
    @MockBean
    private CommuteRecordForAttendancePort commuteRecordForAttendancePort;
    @MockBean
    private AbsentForAttendancePort absentForAttendancePort;

    @Test
    @DisplayName("고용주 - 근무자 결석 처리 - 성공")
    public void markingAbsentSuccess() throws Exception {
        // given
        MarkingAbsentReqDto markingAbsentReqDto = MarkingAbsentReqDto.builder()
                .email("test@email.com")
                .companyId(1L)
                .accountId(2L)
                .build();
        AccountForAttendance accountForAttendance = AccountForAttendance.builder()
                .id(3L)
                .roles("EMPLOYER")
                .build();

        // when
        Mockito.when(accountForAttendancePort.findAccountByEmail(anyString()))
                .thenReturn(accountForAttendance);

        // then
        Assertions.assertDoesNotThrow(() -> attendanceService.markingAbsent(markingAbsentReqDto));
    }

    @Test
    @DisplayName("근무자 - 출근 시간 기록 - 성공")
    public void recordEnterTimeSuccess() throws Exception {
        // given
        RecordEnterTimeReqDto recordEnterTimeReqDto = RecordEnterTimeReqDto.builder()
                .accountId(1L)
                .companyId(2L)
                .tardy(false)
                .build();

        // when

        // then
        Assertions.assertDoesNotThrow(() -> attendanceService.recordEnterTime(recordEnterTimeReqDto));
    }

    @Test
    @DisplayName("근무자 - 퇴근 시간 기록 - 성공")
    public void recordExitTimeSuccess() throws Exception {
        // given
        RecordExitTimeReqDto recordExitTimeReqDto = RecordExitTimeReqDto.builder()
                .accountId(1L)
                .companyId(2L)
                .build();

        // when
        Mockito.when(commuteRecordForAttendancePort.findEnterRecord(any(RecordExitTimeReqDto.class)))
                .thenReturn(3L);
        Mockito.doNothing().when(commuteRecordForAttendancePort)
                .recordExitTime(any(ExitRecord.class), anyLong());

        // then
        Assertions.assertDoesNotThrow(() -> attendanceService.recordExitTime(recordExitTimeReqDto));
    }

    @Test
    @DisplayName("근무자 - 퇴근 시간 기록 실패 - 출근 기록이 없는 경우")
    public void recordExitTimeFailed() throws Exception {
        // given
        RecordExitTimeReqDto recordExitTimeReqDto = RecordExitTimeReqDto.builder()
                .accountId(1L)
                .companyId(2L)
                .build();

        // when
        Mockito.when(commuteRecordForAttendancePort.findEnterRecord(any(RecordExitTimeReqDto.class)))
                .thenThrow(EnterRecordNotFoundException.class);

        // then
        Assertions.assertThrows(EnterRecordNotFoundException.class, () -> attendanceService.recordExitTime(recordExitTimeReqDto));
    }

}