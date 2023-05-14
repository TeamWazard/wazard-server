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
import shop.wazard.application.port.in.AttendanceService;
import shop.wazard.application.port.out.AbsentForAttendancePort;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;
import shop.wazard.dto.MarkingAbsentReqDto;

import static org.mockito.ArgumentMatchers.anyString;

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

}