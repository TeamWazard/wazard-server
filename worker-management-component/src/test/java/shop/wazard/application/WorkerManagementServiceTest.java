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
import shop.wazard.application.domain.AccountForWorkerManagement;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.domain.WaitingStatus;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.application.port.out.AccountForWorkerManagementPort;
import shop.wazard.application.port.out.RosterForWorkerManagementPort;
import shop.wazard.application.port.out.WaitingListForWorkerManagementPort;
import shop.wazard.application.port.out.WorkerManagementPort;
import shop.wazard.dto.PermitWorkerToJoinReqDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WorkerManagementServiceImpl.class})
class WorkerManagementServiceTest {

    @Autowired
    private WorkerManagementService workerManagementService;
    @MockBean
    private WorkerManagementPort workerManagementPort;
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
        Mockito.when(waitingListForWorkerManagementPort.findWaitingInfo(any(PermitWorkerToJoinReqDto.class)))
                .thenReturn(waitingInfo);

        // then
        Assertions.assertDoesNotThrow(() -> workerManagementService.permitWorkerToJoin(permitWorkerToJoinReqDto));
    }


}