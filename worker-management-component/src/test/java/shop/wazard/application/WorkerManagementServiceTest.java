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
import shop.wazard.application.domain.*;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.application.port.out.*;
import shop.wazard.dto.*;
import shop.wazard.exception.JoinWorkerDeniedException;
import shop.wazard.exception.NotAuthorizedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WorkerManagementServiceImpl.class})
class WorkerManagementServiceTest {

    @Autowired
    private WorkerManagementService workerManagementService;
    @MockBean
    private AccountForWorkerManagementPort accountForWorkerManagementPort;
    @MockBean
    private RosterForWorkerManagementPort rosterForWorkerManagementPort;
    @MockBean
    private WaitingListForWorkerManagementPort waitingListForWorkerManagementPort;
    @MockBean
    private CommuteRecordForWorkerManagementPort commuteRecordForWorkerManagementPort;
    @MockBean
    private ReplaceForWorkerManagementPort replaceForWorkerManagementPort;

    @Test
    @DisplayName("고용주 - 근무자 가입 수락 - 성공")
    public void permitWorkerToJoinSuccess() throws Exception {
        // given
        PermitWorkerToJoinReqDto permitWorkerToJoinReqDto = PermitWorkerToJoinReqDto.builder()
                .waitingListId(1L)
                .email("test@email.com")
                .build();
        AccountForWorkerManagement accountForWorkerManagement = AccountForWorkerManagement.builder()
                .id(2L)
                .roles("EMPLOYER")
                .build();
        WaitingInfo waitingInfo = WaitingInfo.builder()
                .accountId(3L)
                .companyId(4L)
                .waitingStatus(WaitingStatus.AGREED)
                .build();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);
        Mockito.when(waitingListForWorkerManagementPort.findWaitingInfo(anyLong()))
                .thenReturn(waitingInfo);

        // then
        Assertions.assertDoesNotThrow(() -> workerManagementService.permitWorkerToJoin(permitWorkerToJoinReqDto));
    }

    @Test
    @DisplayName("고용주 - 계약정보 비동의 근무자(INVITED, JOINED, DISAGREED) 가입 수락 - 실패")
    public void permitDisagreedWorkerToJoin() throws Exception {
        // given
        PermitWorkerToJoinReqDto permitWorkerToJoinReqDto = PermitWorkerToJoinReqDto.builder()
                .waitingListId(1L)
                .email("test@email.com")
                .build();
        AccountForWorkerManagement accountForWorkerManagement = AccountForWorkerManagement.builder()
                .id(2L)
                .roles("EMPLOYER")
                .build();
        WaitingInfo waitingInfo = WaitingInfo.builder()
                .accountId(3L)
                .companyId(4L)
                .waitingStatus(WaitingStatus.DISAGREED)
                .build();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);
        Mockito.when(waitingListForWorkerManagementPort.findWaitingInfo(anyLong()))
                .thenReturn(waitingInfo);

        // then
        Assertions.assertThrows(JoinWorkerDeniedException.class, () -> workerManagementService.permitWorkerToJoin(permitWorkerToJoinReqDto));
    }

    @Test
    @DisplayName("고용주 - 업장 근무자 리스트 조회 - 성공")
    public void getWorkersBelongedToCompanySuccess() throws Exception {
        // given
        Long companyId = 1L;
        WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto = WorkerBelongedToCompanyReqDto.builder()
                .email("test@email.com")
                .build();
        AccountForWorkerManagement accountForWorkerManagement = AccountForWorkerManagement.builder()
                .id(2L)
                .roles("EMPLOYER")
                .build();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);

        // then
        Assertions.assertDoesNotThrow(() -> workerManagementService.getWorkersBelongedCompany(companyId, workerBelongedToCompanyReqDto));
    }

    @Test
    @DisplayName("근무자 - 업장 근무자 리스트 조회 불가능 - 실패")
    public void getWorkersBelongedToCompanyByWorkerFailed() throws Exception {
        // given
        Long companyId = 1L;
        WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto = WorkerBelongedToCompanyReqDto.builder()
                .email("test@email.com")
                .build();
        AccountForWorkerManagement accountForWorkerManagement = AccountForWorkerManagement.builder()
                .id(2L)
                .roles("EMPLOYEE")
                .build();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);

        // then
        Assertions.assertThrows(NotAuthorizedException.class, () -> workerManagementService.getWorkersBelongedCompany(companyId, workerBelongedToCompanyReqDto));
    }

    @Test
    @DisplayName("고용주 - 알바생 퇴출 - 성공")
    void exileWorker() throws Exception {
        // given
        AccountForWorkerManagement accountForWorkerManagement = AccountForWorkerManagement.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();
        ExileWorkerReqDto exileWorkerReqDto = ExileWorkerReqDto.builder()
                .email("test@email.com")
                .accountId(1L)
                .companyId(2L)
                .build();
        RosterForWorkerManagement rosterForWorkerManagement = RosterForWorkerManagement.builder()
                .accountId(1L)
                .companyId(2L)
                .baseStatus(BaseStatus.INACTIVE)
                .build();
        ExileWorkerResDto exileWorkerResDto = ExileWorkerResDto.builder()
                .message("해당 근무자가 업장에서 퇴장되었습니다.")
                .build();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);
        Mockito.when(rosterForWorkerManagementPort.findRoster(anyLong(), anyLong()))
                .thenReturn(rosterForWorkerManagement);
        Mockito.doNothing().when(rosterForWorkerManagementPort).exileWorker(rosterForWorkerManagement);

        // then
        Assertions.assertEquals(workerManagementService.exileWorker(exileWorkerReqDto).getMessage(), exileWorkerResDto.getMessage());
    }

    @Test
    @DisplayName("고용주 - 초대 대기자 목록 조회 - 성공")
    public void getWaitingWorkersSuccess() throws Exception {
        // given
        Long companyId = 10L;
        WaitingWorkerReqDto waitingWorkerReqDto = WaitingWorkerReqDto.builder()
                .email("employer@email.com")
                .build();
        AccountForWorkerManagement accountForWorkerManagement = AccountForWorkerManagement.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();
        List<WaitingWorkerResDto> waitingWorkerResDtoList = setWaitingWorkerResDtoList();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);
        Mockito.when(waitingListForWorkerManagementPort.getWaitingWorker(anyLong()))
                .thenReturn(waitingWorkerResDtoList);

        List<WaitingWorkerResDto> result = workerManagementService.getWaitingWorkers(companyId, waitingWorkerReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(0).getAccountId(), result.get(0).getAccountId()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(0).getUserName(), result.get(0).getUserName()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(0).getEmail(), result.get(0).getEmail()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(0).getWaitingStatus(), result.get(0).getWaitingStatus()),

                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(1).getAccountId(), result.get(1).getAccountId()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(1).getUserName(), result.get(1).getUserName()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(1).getEmail(), result.get(1).getEmail()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(1).getWaitingStatus(), result.get(1).getWaitingStatus()),

                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(2).getAccountId(), result.get(2).getAccountId()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(2).getUserName(), result.get(2).getUserName()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(2).getEmail(), result.get(2).getEmail()),
                () -> Assertions.assertEquals(waitingWorkerResDtoList.get(2).getWaitingStatus(), result.get(2).getWaitingStatus())
        );
    }

    @Test
    @DisplayName("고용주 - 특정 근무자 상세조회 - 성공")
    public void getWorkerAttendanceRecordSuccess() throws Exception {
        // given
        GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto = GetWorkerAttendanceRecordReqDto.builder()
                .email("employer@email.com")
                .accountId(1L)
                .build();

        AccountForWorkerManagement accountForWorkerManagement = AccountForWorkerManagement.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();

        List<CommuteRecordDto> commuteRecordDtoList = setDefaultCommuteRecordDtoList();
        List<AbsentRecordDto> absentRecordDtoList = setDefaultAbsentRecordDtoList();

        GetWorkerAttendanceRecordResDto getWorkerAttendanceRecordResDto = GetWorkerAttendanceRecordResDto.builder()
                .userName("홍길동")
                .commuteRecordResDtoList(commuteRecordDtoList)
                .absentRecordResDtoList(absentRecordDtoList)
                .build();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);
        Mockito.when(commuteRecordForWorkerManagementPort.getWorkerAttendanceRecord(any(GetWorkerAttendanceRecordReqDto.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(getWorkerAttendanceRecordResDto);

        GetWorkerAttendanceRecordResDto result = workerManagementService.getWorkerAttendanceRecord(getWorkerAttendanceRecordReqDto, 2023, 1);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getUserName(), result.getUserName()),

                () -> Assertions.assertEquals(3, result.getCommuteRecordResDtoList().size()),
                () -> Assertions.assertEquals(2, result.getAbsentRecordResDtoList().size()),

                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(0).getCommuteDate(), result.getCommuteRecordResDtoList().get(0).getCommuteDate()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(0).getEnterTime(), result.getCommuteRecordResDtoList().get(0).getEnterTime()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(0).getExitTime(), result.getCommuteRecordResDtoList().get(0).getExitTime()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(0).isTardy(), result.getCommuteRecordResDtoList().get(0).isTardy()),

                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(1).getCommuteDate(), result.getCommuteRecordResDtoList().get(1).getCommuteDate()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(1).getEnterTime(), result.getCommuteRecordResDtoList().get(1).getEnterTime()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(1).getExitTime(), result.getCommuteRecordResDtoList().get(1).getExitTime()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(1).isTardy(), result.getCommuteRecordResDtoList().get(1).isTardy()),

                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(2).getCommuteDate(), result.getCommuteRecordResDtoList().get(2).getCommuteDate()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(2).getEnterTime(), result.getCommuteRecordResDtoList().get(2).getEnterTime()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(2).getExitTime(), result.getCommuteRecordResDtoList().get(2).getExitTime()),
                () -> Assertions.assertEquals(getWorkerAttendanceRecordResDto.getCommuteRecordResDtoList().get(2).isTardy(), result.getCommuteRecordResDtoList().get(2).isTardy()),

                () -> Assertions.assertEquals(absentRecordDtoList.get(0).getAbsentDate(), result.getAbsentRecordResDtoList().get(0).getAbsentDate()),
                () -> Assertions.assertEquals(absentRecordDtoList.get(1).getAbsentDate(), result.getAbsentRecordResDtoList().get(1).getAbsentDate())
        );
    }

    @Test
    @DisplayName("고용주 - 특정 근무자 상세조회 - 실패" +
            "지원되지 않는 날짜를 조회하는 경우")
    public void getWorkerAttendanceRecordFailedByUnsupportedDate() throws Exception {
        // given
        GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto = GetWorkerAttendanceRecordReqDto.builder()
                .email("employer@email.com")
                .accountId(1L)
                .build();

        AccountForWorkerManagement accountForWorkerManagement = AccountForWorkerManagement.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();

        List<CommuteRecordDto> commuteRecordDtoList = setDefaultCommuteRecordDtoList();
        List<AbsentRecordDto> absentRecordDtoList = setDefaultAbsentRecordDtoList();

        GetWorkerAttendanceRecordResDto getWorkerAttendanceRecordResDto = GetWorkerAttendanceRecordResDto.builder()
                .userName("홍길동")
                .commuteRecordResDtoList(commuteRecordDtoList)
                .absentRecordResDtoList(absentRecordDtoList)
                .build();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);
        Mockito.when(commuteRecordForWorkerManagementPort.getWorkerAttendanceRecord(any(GetWorkerAttendanceRecordReqDto.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(getWorkerAttendanceRecordResDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> workerManagementService.getWorkerAttendanceRecord(getWorkerAttendanceRecordReqDto, 1999, 1)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> workerManagementService.getWorkerAttendanceRecord(getWorkerAttendanceRecordReqDto, 2024, 1))
        );
    }

    @Test
    @DisplayName("고용주 - 전체대타 기록 조회- 성공")
    public void getAllReplaceSuccess() throws Exception {
        // given
        GetAllReplaceRecordReqDto getAllReplaceRecordReqDto = GetAllReplaceRecordReqDto.builder()
                .email("test@email.com")
                .companyId(1L)
                .build();
        AccountForWorkerManagement accountForWorkerManagement = setDefaultEmployerAccountForWorkerManagement();
        List<GetAllReplaceRecordResDto> getAllReplaceRecordResDtoList = setDefaultReplaceList();

        // when
        Mockito.when(accountForWorkerManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorkerManagement);
        Mockito.when(replaceForWorkerManagementPort.getAllReplaceRecord(any(GetAllReplaceRecordReqDto.class)))
                .thenReturn(getAllReplaceRecordResDtoList);
        List<GetAllReplaceRecordResDto> result = workerManagementService.getAllReplaceRecord(getAllReplaceRecordReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(0).getUserName(), result.get(0).getUserName()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(0).getReplaceWorkerName(), result.get(0).getReplaceWorkerName()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(0).getReplaceDate(), result.get(0).getReplaceDate()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(0).getEnterTime(), result.get(0).getEnterTime()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(0).getExitTime(), result.get(0).getExitTime()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(1).getUserName(), result.get(1).getUserName()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(1).getReplaceWorkerName(), result.get(1).getReplaceWorkerName()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(1).getReplaceDate(), result.get(1).getReplaceDate()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(1).getEnterTime(), result.get(1).getEnterTime()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(1).getExitTime(), result.get(1).getExitTime()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(2).getUserName(), result.get(2).getUserName()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(2).getReplaceWorkerName(), result.get(2).getReplaceWorkerName()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(2).getReplaceDate(), result.get(2).getReplaceDate()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(2).getEnterTime(), result.get(2).getEnterTime()),
                () -> Assertions.assertEquals(getAllReplaceRecordResDtoList.get(2).getExitTime(), result.get(2).getExitTime())
        );
    }

    private List<CommuteRecordDto> setDefaultCommuteRecordDtoList() {
        List<CommuteRecordDto> commuteRecordDtoList = new ArrayList<>();

        CommuteRecordDto commuteRecordDto1 = CommuteRecordDto.builder()
                .commuteDate(LocalDate.of(2023, 1, 2))
                .enterTime(LocalDateTime.of(2023, 1, 1, 10, 20))
                .exitTime(LocalDateTime.of(2023, 1, 1, 13, 0))
                .tardy(true)
                .build();
        CommuteRecordDto commuteRecordDto2 = CommuteRecordDto.builder()
                .commuteDate(LocalDate.of(2023, 1, 5))
                .enterTime(LocalDateTime.of(2023, 1, 5, 17, 50))
                .exitTime(LocalDateTime.of(2023, 1, 5, 19, 30))
                .tardy(false)
                .build();
        CommuteRecordDto commuteRecordDto3 = CommuteRecordDto.builder()
                .commuteDate(LocalDate.of(2023, 1, 14))
                .enterTime(LocalDateTime.of(2023, 1, 14, 20, 20))
                .exitTime(LocalDateTime.of(2023, 1, 14, 21, 0))
                .tardy(false)
                .build();

        commuteRecordDtoList.add(commuteRecordDto1);
        commuteRecordDtoList.add(commuteRecordDto2);
        commuteRecordDtoList.add(commuteRecordDto3);

        return commuteRecordDtoList;
    }

    private List<AbsentRecordDto> setDefaultAbsentRecordDtoList() {
        List<AbsentRecordDto> absentRecordDtoList = new ArrayList<>();

        AbsentRecordDto absentRecordDto1 = AbsentRecordDto.builder()
                .absentDate(LocalDate.of(2023, 1, 1))
                .build();
        AbsentRecordDto absentRecordDto2 = AbsentRecordDto.builder()
                .absentDate(LocalDate.of(2023, 1, 10))
                .build();

        absentRecordDtoList.add(absentRecordDto1);
        absentRecordDtoList.add(absentRecordDto2);

        return absentRecordDtoList;
    }

    private List<WaitingWorkerResDto> setWaitingWorkerResDtoList() {
        List<WaitingWorkerResDto> waitingWorkerResDtoList = new ArrayList<>();
        waitingWorkerResDtoList.add(WaitingWorkerResDto.builder()
                .accountId(1L)
                .userName("testName1")
                .waitingStatus(WaitingStatus.AGREED)
                .email("test1@email.com")
                .build());
        waitingWorkerResDtoList.add(WaitingWorkerResDto.builder()
                .accountId(2L)
                .userName("testName2")
                .waitingStatus(WaitingStatus.DISAGREED)
                .email("test2@email.com")
                .build());
        waitingWorkerResDtoList.add(WaitingWorkerResDto.builder()
                .accountId(4L)
                .userName("testName4")
                .waitingStatus(WaitingStatus.INVITED)
                .email("test4@email.com")
                .build());
        return waitingWorkerResDtoList;
    }

    private AccountForWorkerManagement setDefaultEmployerAccountForWorkerManagement() {
        return AccountForWorkerManagement.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();
    }

    private List<GetAllReplaceRecordResDto> setDefaultReplaceList() {
        List<GetAllReplaceRecordResDto> getAllReplaceRecordResDtoList = new ArrayList<>();
        GetAllReplaceRecordResDto getAllReplaceRecordResDto1 = GetAllReplaceRecordResDto.builder()
                .userName("test1")
                .replaceWorkerName("test2")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();
        GetAllReplaceRecordResDto getAllReplaceRecordResDto2 = GetAllReplaceRecordResDto.builder()
                .userName("test2")
                .replaceWorkerName("test3")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();
        GetAllReplaceRecordResDto getAllReplaceRecordResDto3 = GetAllReplaceRecordResDto.builder()
                .userName("test3")
                .replaceWorkerName("test1")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();
        getAllReplaceRecordResDtoList.add(getAllReplaceRecordResDto1);
        getAllReplaceRecordResDtoList.add(getAllReplaceRecordResDto2);
        getAllReplaceRecordResDtoList.add(getAllReplaceRecordResDto3);
        return getAllReplaceRecordResDtoList;
    }

}