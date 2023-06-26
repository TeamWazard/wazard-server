package shop.wazard.application;

import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import shop.wazard.application.domain.ContractInfo;
import shop.wazard.application.port.in.WorkerService;
import shop.wazard.application.port.out.AccountForWorkerPort;
import shop.wazard.application.port.out.ContractForWorkerPort;
import shop.wazard.application.port.out.WorkerPort;
import shop.wazard.dto.CheckAgreementReqDto;
import shop.wazard.dto.GetMyReplaceRecordReqDto;
import shop.wazard.dto.GetMyReplaceRecordResDto;
import shop.wazard.dto.RegisterReplaceReqDto;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WorkerServiceImpl.class})
class WorkerServiceImplTest {

    @Autowired private WorkerService workerService;
    @MockBean private WorkerPort workerPort;
    @MockBean private AccountForWorkerPort accountForWorkerPort;
    @MockBean private ContractForWorkerPort contractForWorkerPort;

    @Test
    @DisplayName("근무자 - 계약정보 동의/비동의 체크 - 성공")
    void checkAgreementSuccess() throws Exception {
        // given
        CheckAgreementReqDto checkAgreementReqDto =
                CheckAgreementReqDto.builder().contractId(1L).agreementCheck(true).build();
        ContractInfo contractInfo =
                ContractInfo.builder().contractId(1L).contractInfoAgreement(false).build();

        // when
        Mockito.when(contractForWorkerPort.findContractJpaByContractId(anyLong()))
                .thenReturn(contractInfo);
        contractInfo.changeContractAgreementState(checkAgreementReqDto);

        // then
        Assertions.assertAll(
                () ->
                        Assertions.assertEquals(
                                checkAgreementReqDto.isAgreementCheck(),
                                contractInfo.isContractInfoAgreement()),
                () ->
                        Assertions.assertEquals(
                                "동의하였습니다.",
                                workerService.checkAgreement(checkAgreementReqDto).getMessage()));
    }

    @Test
    @DisplayName("근무자 - 대타 등록 - 성공")
    public void registerReplaceSucces() throws Exception {
        // given
        RegisterReplaceReqDto registerReplaceReqDto =
                RegisterReplaceReqDto.builder()
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
    public void getMyReplaceRecordSuccess() throws Exception {
        // given
        Long companyId = 1L;
        GetMyReplaceRecordReqDto getMyReplaceRecordReqDto =
                GetMyReplaceRecordReqDto.builder().email("test@email.com").build();
        AccountForWorker accountForWorker = setDefaultEmployeeAccountForWorker();
        List<GetMyReplaceRecordResDto> getMyReplaceRecordResDtoList = setDefaultReplaceRecordList();

        // when
        Mockito.when(accountForWorkerPort.findAccountByEmail(anyString()))
                .thenReturn(accountForWorker);
        Mockito.when(workerPort.getMyReplaceRecord(any(GetMyReplaceRecordReqDto.class), anyLong()))
                .thenReturn(getMyReplaceRecordResDtoList);
        List<GetMyReplaceRecordResDto> result =
                workerService.getMyReplaceRecord(getMyReplaceRecordReqDto, companyId);

        // then
        Assertions.assertAll(
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(0).getUserName(),
                                result.get(0).getUserName()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(0).getReplaceWorkerName(),
                                result.get(0).getReplaceWorkerName()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(0).getReplaceDate(),
                                result.get(0).getReplaceDate()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(0).getEnterTime(),
                                result.get(0).getEnterTime()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(0).getExitTime(),
                                result.get(0).getExitTime()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(1).getUserName(),
                                result.get(1).getUserName()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(1).getReplaceWorkerName(),
                                result.get(1).getReplaceWorkerName()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(1).getReplaceDate(),
                                result.get(1).getReplaceDate()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(1).getEnterTime(),
                                result.get(1).getEnterTime()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(1).getExitTime(),
                                result.get(1).getExitTime()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(2).getUserName(),
                                result.get(2).getUserName()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(2).getReplaceWorkerName(),
                                result.get(2).getReplaceWorkerName()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(2).getReplaceDate(),
                                result.get(2).getReplaceDate()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(2).getEnterTime(),
                                result.get(2).getEnterTime()),
                () ->
                        Assertions.assertEquals(
                                getMyReplaceRecordResDtoList.get(2).getExitTime(),
                                result.get(2).getExitTime()),
                () -> Assertions.assertEquals(getMyReplaceRecordResDtoList.size(), result.size()),
                () -> Assertions.assertNotEquals("test1", result.get(0).getUserName()),
                () -> Assertions.assertNotEquals("test1", result.get(1).getUserName()),
                () -> Assertions.assertNotEquals("test1", result.get(2).getUserName()));
    }

    private AccountForWorker setDefaultEmployeeAccountForWorker() {
        return AccountForWorker.builder()
                .id(1L)
                .roles("EMPLOYEE")
                .userName("test")
                .email("test@email.com")
                .build();
    }

    private List<GetMyReplaceRecordResDto> setDefaultReplaceRecordList() {
        List<GetMyReplaceRecordResDto> getMyReplaceRecordResDtoList = new ArrayList<>();
        GetMyReplaceRecordResDto getMyReplaceRecordResDto1 =
                GetMyReplaceRecordResDto.builder()
                        .userName("test")
                        .replaceWorkerName("test1")
                        .replaceDate(LocalDate.now())
                        .enterTime(LocalDateTime.now())
                        .exitTime(LocalDateTime.now())
                        .build();
        GetMyReplaceRecordResDto getMyReplaceRecordResDto2 =
                GetMyReplaceRecordResDto.builder()
                        .userName("test")
                        .replaceWorkerName("test1")
                        .replaceDate(LocalDate.now())
                        .enterTime(LocalDateTime.now())
                        .exitTime(LocalDateTime.now())
                        .build();
        GetMyReplaceRecordResDto getMyReplaceRecordResDto3 =
                GetMyReplaceRecordResDto.builder()
                        .userName("test")
                        .replaceWorkerName("test1")
                        .replaceDate(LocalDate.now())
                        .enterTime(LocalDateTime.now())
                        .exitTime(LocalDateTime.now())
                        .build();
        getMyReplaceRecordResDtoList.add(getMyReplaceRecordResDto1);
        getMyReplaceRecordResDtoList.add(getMyReplaceRecordResDto2);
        getMyReplaceRecordResDtoList.add(getMyReplaceRecordResDto3);
        return getMyReplaceRecordResDtoList;
    }
}
