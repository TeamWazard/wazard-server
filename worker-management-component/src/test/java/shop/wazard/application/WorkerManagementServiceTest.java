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
import shop.wazard.application.port.out.AccountForWorkerManagementPort;
import shop.wazard.application.port.out.RosterForWorkerManagementPort;
import shop.wazard.application.port.out.WaitingListForWorkerManagementPort;
import shop.wazard.dto.ExileWorkerReqDto;
import shop.wazard.dto.ExileWorkerResDto;
import shop.wazard.dto.PermitWorkerToJoinReqDto;
import shop.wazard.dto.WorkerBelongedToCompanyReqDto;
import shop.wazard.exception.JoinWorkerDeniedException;
import shop.wazard.exception.NotAuthorizedException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

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
        WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto = WorkerBelongedToCompanyReqDto.builder()
                .companyId(1L)
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
        Assertions.assertDoesNotThrow(() -> workerManagementService.getWorkersBelongedCompany(workerBelongedToCompanyReqDto));
    }

    @Test
    @DisplayName("근무자 - 업장 근무자 리스트 조회 불가능 - 실패")
    public void getWorkersBelongedToCompanyByWorkerFailed() throws Exception {
        // given
        WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto = WorkerBelongedToCompanyReqDto.builder()
                .companyId(1L)
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
        Assertions.assertThrows(NotAuthorizedException.class, () -> workerManagementService.getWorkersBelongedCompany(workerBelongedToCompanyReqDto));
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
        Mockito.when(rosterForWorkerManagementPort.findRoster(exileWorkerReqDto.getAccountId(), exileWorkerReqDto.getCompanyId()))
                .thenReturn(rosterForWorkerManagement);
        Mockito.doNothing().when(rosterForWorkerManagementPort).exileWorker(rosterForWorkerManagement);

        // then
        Assertions.assertEquals(workerManagementService.exileWorker(exileWorkerReqDto).getMessage(), exileWorkerResDto.getMessage());
    }

}