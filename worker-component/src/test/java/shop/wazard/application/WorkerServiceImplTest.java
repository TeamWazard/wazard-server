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
import shop.wazard.application.domain.AccountForWorker;
import shop.wazard.application.port.in.WorkerService;
import shop.wazard.application.port.out.AccountForWorkerPort;
import shop.wazard.application.port.out.WorkerPort;
import shop.wazard.dto.RegisterReplaceReqDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WorkerServiceImpl.class})
class WorkerServiceImplTest {

    @Autowired
    private WorkerService workerService;
    @MockBean
    private WorkerPort workerPort;
    @MockBean
    private AccountForWorkerPort accountForWorkerPort;

    @Test
    @DisplayName("근무자 - 대타 등록 - 성공")
    public void registerReplaceSucces() throws Exception {
        // given
        RegisterReplaceReqDto registerReplaceReqDto = RegisterReplaceReqDto.builder()
                .companyId(1L)
                .replaceWorkerName("test1")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();
        AccountForWorker accountForWorker = setDefaultEmployeeAccountForWorker();

        // when
        Mockito.when(accountForWorkerPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorker);


        // then
        Assertions.assertDoesNotThrow(() -> workerService.registerReplace(registerReplaceReqDto));
    }

    private AccountForWorker setDefaultEmployeeAccountForWorker() {
        return AccountForWorker.builder()
                .id(1L)
                .roles("EMPLOYEE")
                .userName("test")
                .email("test@email.com")
                .build();
    }
}