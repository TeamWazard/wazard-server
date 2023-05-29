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
import shop.wazard.dto.GetMyReplaceReqDto;
import shop.wazard.dto.GetMyReplaceResDto;
import shop.wazard.dto.RegisterReplaceReqDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                .email("test@email.com")
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

    @Test
    @DisplayName("근무자 - 대타 기록 조회 - 성공")
    public void getMyReplace() throws Exception {
        // given
        GetMyReplaceReqDto getMyReplaceReqDto = GetMyReplaceReqDto.builder()
                .email("test@email.com")
                .companyId(1L)
                .build();
        AccountForWorker accountForWorker = setDefaultEmployeeAccountForWorker();
        List<GetMyReplaceResDto> getMyReplaceResDtoList = setDefaultReplaceList();

        // when
        Mockito.when(accountForWorkerPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorker);
        Mockito.when(workerPort.getMyReplace(getMyReplaceReqDto))
                .thenReturn(getMyReplaceResDtoList);
        List<GetMyReplaceResDto> result = workerService.getMyReplace(getMyReplaceReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(getMyReplaceResDtoList.get(0).getUserName(), result.get(0).getUserName()),
                () -> Assertions.assertEquals(getMyReplaceResDtoList.get(1).getUserName(), result.get(1).getUserName()),
                () -> Assertions.assertEquals(getMyReplaceResDtoList.get(2).getUserName(), result.get(2).getUserName())
        );
    }

    private AccountForWorker setDefaultEmployeeAccountForWorker() {
        return AccountForWorker.builder()
                .id(1L)
                .roles("EMPLOYEE")
                .userName("test")
                .email("test@email.com")
                .build();
    }

    private List<GetMyReplaceResDto> setDefaultReplaceList() {
        List<GetMyReplaceResDto> getMyReplaceResDtoList = new ArrayList<>();
        GetMyReplaceResDto getMyReplaceResDto1 = GetMyReplaceResDto.builder()
                .userName("test")
                .replaceWorkerName("test1")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();
        GetMyReplaceResDto getMyReplaceResDto2 = GetMyReplaceResDto.builder()
                .userName("test")
                .replaceWorkerName("test1")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();
        GetMyReplaceResDto getMyReplaceResDto3 = GetMyReplaceResDto.builder()
                .userName("test")
                .replaceWorkerName("test1")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();
        getMyReplaceResDtoList.add(getMyReplaceResDto1);
        getMyReplaceResDtoList.add(getMyReplaceResDto2);
        getMyReplaceResDtoList.add(getMyReplaceResDto3);
        return getMyReplaceResDtoList;
    }

}