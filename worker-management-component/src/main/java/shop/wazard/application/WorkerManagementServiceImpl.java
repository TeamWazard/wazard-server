package shop.wazard.application;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForWorkerManagement;
import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.domain.WorkRecordForWorkerManagement;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.application.port.out.*;
import shop.wazard.dto.*;
import shop.wazard.exception.UnsupportedDateException;
import shop.wazard.util.calculator.Calculator;
import shop.wazard.util.exception.StatusEnum;

@Transactional
@Service
@RequiredArgsConstructor
class WorkerManagementServiceImpl implements WorkerManagementService {

    private final AccountForWorkerManagementPort accountForWorkerManagementPort;
    private final RosterForWorkerManagementPort rosterForWorkerManagementPort;
    private final WaitingListForWorkerManagementPort waitingListForWorkerManagementPort;
    private final ReplaceForWorkerManagementPort replaceForWorkerManagementPort;
    private final CommuteRecordForWorkerManagementPort commuteRecordForWorkerManagementPort;
    private final WorkRecordForWorkerManagementPort workRecordForWorkerManagementPort;
    private final ContractForWorkerManagementPort contractForWorkerManagementPort;

    @Override
    public PermitWorkerToJoinResDto permitWorkerToJoin(
            PermitWorkerToJoinReqDto permitWorkerToJoinReqDto) {
        AccountForWorkerManagement accountForWorkerManagement =
                accountForWorkerManagementPort.findAccountByEmail(
                        permitWorkerToJoinReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        WaitingInfo waitingInfo =
                waitingListForWorkerManagementPort.findWaitingInfo(
                        permitWorkerToJoinReqDto.getWaitingListId());
        waitingInfo.changeWaitingStatus();
        waitingListForWorkerManagementPort.updateWaitingStatus(waitingInfo);
        rosterForWorkerManagementPort.joinWorker(
                RosterForWorkerManagement.createRosterForWorkerManagement(waitingInfo));
        return PermitWorkerToJoinResDto.builder().message("업장 근무자 명단에 추가되었습니다.").build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkerBelongedToCompanyResDto> getWorkersBelongedCompany(
            Long companyId, WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto) {
        AccountForWorkerManagement accountForWorkerManagement =
                accountForWorkerManagementPort.findAccountByEmail(
                        workerBelongedToCompanyReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        return rosterForWorkerManagementPort.getWorkersBelongedToCompany(companyId);
    }

    @Override
    public ExileWorkerResDto exileWorker(ExileWorkerReqDto exileWorkerReqDto) {
        AccountForWorkerManagement accountForWorkerManagement =
                accountForWorkerManagementPort.findAccountByEmail(exileWorkerReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        RosterForWorkerManagement rosterForWorkerManagement =
                rosterForWorkerManagementPort.findRoster(
                        exileWorkerReqDto.getAccountId(), exileWorkerReqDto.getCompanyId());
        rosterForWorkerManagement.updateRosterStateForExile();
        rosterForWorkerManagementPort.exileWorker(rosterForWorkerManagement);
        return ExileWorkerResDto.builder().message("해당 근무자가 업장에서 퇴장되었습니다.").build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<WaitingWorkerResDto> getWaitingWorkers(
            Long companyId, WaitingWorkerReqDto waitingWorkerReqDto) {
        AccountForWorkerManagement accountForWorkerManagement =
                accountForWorkerManagementPort.findAccountByEmail(waitingWorkerReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        return waitingListForWorkerManagementPort.getWaitingWorker(companyId);
    }

    @Transactional(readOnly = true)
    @Override
    public GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(
            GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto, int year, int month) {
        AccountForWorkerManagement accountForWorkerManagement =
                accountForWorkerManagementPort.findAccountByEmail(
                        getWorkerAttendanceRecordReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        if (isInvalidDate(year, month)) {
            throw new UnsupportedDateException(StatusEnum.UNSUPPORTED_DATE_RANGE.getMessage());
        }
        LocalDate startDate = getDate(year, month);
        LocalDate endDate = getEndDate(year, month);
        return commuteRecordForWorkerManagementPort.getWorkerAttendanceRecord(
                getWorkerAttendanceRecordReqDto, startDate, endDate);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetAllReplaceRecordResDto> getAllReplaceRecord(
            GetAllReplaceRecordReqDto getAllReplaceRecordReqDto) {
        AccountForWorkerManagement accountForWorkerManagement =
                accountForWorkerManagementPort.findAccountByEmail(
                        getAllReplaceRecordReqDto.getEmail());
        accountForWorkerManagement.checkIsEmployer();
        return replaceForWorkerManagementPort.getAllReplaceRecord(getAllReplaceRecordReqDto);
    }

    @Transactional(readOnly = true)
    @Override
    public GetWorkerAttitudeScoreResDto getWorkerAttitudeScore(
            GetWorkerAttitudeScoreReqDto getWorkerAttitudeScoreReqDto) {
        AccountForWorkerManagement accountForMyPage =
                accountForWorkerManagementPort.findAccountByEmail(
                        getWorkerAttitudeScoreReqDto.getEmail());
        accountForMyPage.checkIsEmployer();
        List<WorkRecordForWorkerManagement> workerTotalPastWorkRecord =
                workRecordForWorkerManagementPort.getWorkerTotalPastRecord(
                        getWorkerAttitudeScoreReqDto.getAccountId());
        List<Double> totalWorkerAttitudeScores =
                getCalculatedAttitudeScore(workerTotalPastWorkRecord);
        double workerAttitudeScore = Calculator.getAverageAttitudeScore(totalWorkerAttitudeScores);
        return GetWorkerAttitudeScoreResDto.builder()
                .workerAttitudeScore(workerAttitudeScore)
                .build();
    }

    @Override
    public RegisterContractInfoResDto registerContractInfo(
            RegisterContractInfoReqDto registerContractInfoReqDto) {
        contractForWorkerManagementPort.registerContractInfo(registerContractInfoReqDto);
        waitingListForWorkerManagementPort.addWaitingInfo(
                registerContractInfoReqDto.getAccountId(),
                registerContractInfoReqDto.getCompanyId());
        return RegisterContractInfoResDto.builder().message("초기 계약 정보가 저장되었습니다.").build();
    }

    private List<Double> getCalculatedAttitudeScore(
            List<WorkRecordForWorkerManagement> workerTotalPastWorkRecord) {
        return workerTotalPastWorkRecord.stream()
                .map(
                        record ->
                                Calculator.getAttitudeScore(
                                        record.getTardyCount(),
                                        record.getAbsentCount(),
                                        record.getWorkDayCount()))
                .collect(Collectors.toList());
    }

    private boolean isInvalidDate(int year, int month) {
        boolean flag = false;

        // 2000년 ~ 현재 년도까지만 조회가능
        if (year < 2000 || LocalDate.now().getYear() < year) {
            flag = true;
        }
        // 1월 ~ 12월 까지만 조회 가능
        if (month < 1 || 12 < month) {
            flag = true;
        }

        return flag;
    }

    private LocalDate getDate(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    private LocalDate getEndDate(int year, int month) {
        int nextMonth = getNextMonth(month);
        int calculatedYear = calcYearByMonth(year, nextMonth);
        return getDate(calculatedYear, nextMonth);
    }

    private int calcYearByMonth(int year, int month) {
        if (month == 1) {
            year++;
        }
        return year;
    }

    private int getNextMonth(int month) {
        if (++month == 13) {
            month = 1;
        }
        return month;
    }
}
