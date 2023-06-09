package shop.wazard.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AccountForWorkerManagement;
import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.domain.WorkRecordForWorkerManagement;
import shop.wazard.application.port.out.*;
import shop.wazard.dto.*;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.AbsentJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.*;
import shop.wazard.entity.contract.ContractJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.exception.RosterNotFoundException;
import shop.wazard.exception.WorkerNotFoundInWaitingListException;
import shop.wazard.util.exception.StatusEnum;

@Repository
@RequiredArgsConstructor
class WorkerManagementDbAdapter
        implements AccountForWorkerManagementPort,
                RosterForWorkerManagementPort,
                WaitingListForWorkerManagementPort,
                CommuteRecordForWorkerManagementPort,
                ReplaceForWorkerManagementPort,
                WorkRecordForWorkerManagementPort,
                ContractForWorkerManagementPort {

    private final WorkerManagementMapper workerForManagementMapper;
    private final AccountForWorkerManagementMapper accountForWorkerManagementMapper;
    private final AccountJpaForWorkerManagementRepository accountJpaForWorkerManagementRepository;
    private final CompanyJpaForWorkerManagementRepository companyJpaForWorkerManagementRepository;
    private final RosterJpaForWorkerManagementRepository rosterJpaForWorkerManagementRepository;
    private final WaitingListJpaForWorkerManagementRepository
            waitingListJpaForWorkerManagementRepository;
    private final EnterRecordJpaForWorkerManagementRepository
            enterRecordJpaForWorkerManagementRepository;
    private final AbsentRecordJpaForWorkerManagementRepository
            absentRecordJpaForWorkerManagementRepository;
    private final ExitRecordJpaForWorkerManagementRepository
            exitRecordJpaForWorkerManagementRepository;
    private final ReplaceJpaForWorkerManagementRepository replaceJpaForWorkerManagementRepository;
    private final ContractForWorkerManagementRepository contractForWorkerManagementRepository;

    @Override
    public void joinWorker(RosterForWorkerManagement rosterForWorkerManagement) {
        AccountJpa accountJpa =
                accountJpaForWorkerManagementRepository
                        .findById(rosterForWorkerManagement.getAccountId())
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa =
                companyJpaForWorkerManagementRepository
                        .findById(rosterForWorkerManagement.getCompanyId())
                        .orElseThrow(
                                () ->
                                        new CompanyNotFoundException(
                                                StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        RosterJpa rosterJpa =
                RosterJpa.builder()
                        .accountJpa(accountJpa)
                        .companyJpa(companyJpa)
                        .rosterTypeJpa(
                                RosterTypeJpa.valueOf(
                                        rosterForWorkerManagement.getRelationType().getType()))
                        .build();
        rosterJpaForWorkerManagementRepository.save(rosterJpa);
    }

    @Override
    public WaitingInfo findWaitingInfo(Long waitingListId) {
        WaitingListJpa waitingListJpa =
                waitingListJpaForWorkerManagementRepository
                        .findById(waitingListId)
                        .orElseThrow(
                                () ->
                                        new WorkerNotFoundInWaitingListException(
                                                StatusEnum.WORKER_NOT_FOUND_IN_WAITING_LIST
                                                        .getMessage()));
        return workerForManagementMapper.toWaitingInfoDomain(waitingListJpa);
    }

    @Override
    public AccountForWorkerManagement findAccountByEmail(String email) {
        AccountJpa accountJpa =
                accountJpaForWorkerManagementRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountForWorkerManagementMapper.toAccountDomain(accountJpa);
    }

    @Override
    public void updateWaitingStatus(WaitingInfo waitingInfo) {
        WaitingListJpa waitingListJpa =
                waitingListJpaForWorkerManagementRepository
                        .findById(waitingInfo.getWaitingListId())
                        .orElseThrow(
                                () ->
                                        new WorkerNotFoundInWaitingListException(
                                                StatusEnum.WORKER_NOT_FOUND_IN_WAITING_LIST
                                                        .getMessage()));
        workerForManagementMapper.updateWaitingStatus(waitingListJpa, waitingInfo);
    }

    @Override
    public List<WorkerBelongedToCompanyResDto> getWorkersBelongedToCompany(Long companyId) {
        List<AccountJpa> workersBelongedCompany =
                accountJpaForWorkerManagementRepository.findWorkersBelongedToCompany(companyId);
        return workerForManagementMapper.toWorkersBelongedToCompany(workersBelongedCompany);
    }

    @Override
    public RosterForWorkerManagement findRoster(Long accountId, Long companyId) {
        RosterJpa rosterJpa =
                rosterJpaForWorkerManagementRepository
                        .findRosterJpaByAccountIdAndCompanyId(accountId, companyId)
                        .orElseThrow(
                                () ->
                                        new RosterNotFoundException(
                                                StatusEnum.ROSTER_NOT_FOUND.getMessage()));
        return workerForManagementMapper.toRosterDomain(rosterJpa);
    }

    @Override
    public void exileWorker(RosterForWorkerManagement rosterForWorkerManagement) {
        RosterJpa rosterJpa =
                rosterJpaForWorkerManagementRepository
                        .findById(rosterForWorkerManagement.getRosterId())
                        .orElseThrow(
                                () ->
                                        new RosterNotFoundException(
                                                StatusEnum.ROSTER_NOT_FOUND.getMessage()));
        workerForManagementMapper.updateRosterStateForExile(rosterJpa, rosterForWorkerManagement);
    }

    @Override
    public List<WaitingWorkerResDto> getWaitingWorker(Long companyId) {
        List<WaitingListJpa> waitingWorkerJpaList =
                waitingListJpaForWorkerManagementRepository.findWaitingWorkers(companyId);
        return workerForManagementMapper.toWaitingWorkerList(waitingWorkerJpaList);
    }

    @Override
    public void addWaitingInfo(Long accountId, Long companyId) {
        AccountJpa accountJpa =
                accountJpaForWorkerManagementRepository
                        .findById(accountId)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa =
                companyJpaForWorkerManagementRepository
                        .findById(companyId)
                        .orElseThrow(
                                () ->
                                        new CompanyNotFoundException(
                                                StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        WaitingListJpa waitingListJpa =
                WaitingListJpa.builder()
                        .accountJpa(accountJpa)
                        .companyJpa(companyJpa)
                        .waitingStatusJpa(WaitingStatusJpa.INVITED)
                        .build();
        waitingListJpaForWorkerManagementRepository.save(waitingListJpa);
    }

    @Override
    public GetWorkerAttendanceRecordResDto getWorkerAttendanceRecord(
            GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto,
            LocalDate startDate,
            LocalDate endDate) {
        AccountJpa accountJpa =
                accountJpaForWorkerManagementRepository
                        .findById(getWorkerAttendanceRecordReqDto.getAccountId())
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        List<EnterRecordJpa> enterRecordJpaList =
                enterRecordJpaForWorkerManagementRepository.findAllRecordOfWorker(
                        getWorkerAttendanceRecordReqDto.getAccountId(),
                        getWorkerAttendanceRecordReqDto.getCompanyId(),
                        startDate,
                        endDate);
        List<AbsentJpa> absentJpaList =
                absentRecordJpaForWorkerManagementRepository.findAllAbsentRecordOfWorker(
                        getWorkerAttendanceRecordReqDto.getAccountId(),
                        getWorkerAttendanceRecordReqDto.getCompanyId(),
                        startDate,
                        endDate);
        return workerForManagementMapper.toWorkerAttendaceRecord(
                accountJpa, enterRecordJpaList, absentJpaList);
    }

    @Override
    public List<GetAllReplaceRecordResDto> getAllReplaceRecord(
            GetAllReplaceRecordReqDto getAllReplaceRecordReqDto) {
        CompanyJpa companyJpa =
                companyJpaForWorkerManagementRepository
                        .findById(getAllReplaceRecordReqDto.getCompanyId())
                        .orElseThrow(
                                () ->
                                        new CompanyNotFoundException(
                                                StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        List<ReplaceWorkerJpa> replaceWorkerJpaList =
                replaceJpaForWorkerManagementRepository.findAllReplaceRecord(companyJpa.getId());
        return workerForManagementMapper.toAllReplaceRecord(replaceWorkerJpaList);
    }

    @Override
    public List<WorkRecordForWorkerManagement> getWorkerTotalPastRecord(Long accountId) {
        List<CompanyJpa> pastCompanies =
                companyJpaForWorkerManagementRepository.findAllWorkerPastCompaniesByAccountId(
                        accountId);
        return pastCompanies.stream()
                .map(company -> getWorkerPastWorkRecord(accountId, company.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void registerContractInfo(RegisterContractInfoReqDto registerContractInfoReqDto) {
        AccountJpa accountJpa =
                accountJpaForWorkerManagementRepository
                        .findById(registerContractInfoReqDto.getAccountId())
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa =
                companyJpaForWorkerManagementRepository
                        .findById(registerContractInfoReqDto.getCompanyId())
                        .orElseThrow(
                                () ->
                                        new CompanyNotFoundException(
                                                StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        contractForWorkerManagementRepository.save(
                ContractJpa.builder()
                        .accountJpa(accountJpa)
                        .companyJpa(companyJpa)
                        .inviteCode(registerContractInfoReqDto.getInvitationCode())
                        .startPeriod(registerContractInfoReqDto.getStartDate())
                        .endPeriod(registerContractInfoReqDto.getEndDate())
                        .workPlaceAddress(registerContractInfoReqDto.getAddress())
                        .workTime(registerContractInfoReqDto.getWorkingTime())
                        .wage(registerContractInfoReqDto.getWage())
                        .contractInfoAgreement(registerContractInfoReqDto.isContractInfoAgreement())
                        .build());
    }

    private WorkRecordForWorkerManagement getWorkerPastWorkRecord(Long accountId, Long companyId) {
        return WorkRecordForWorkerManagement.builder()
                .tardyCount(getTardyCount(accountId, companyId))
                .absentCount(getAbsentCount(accountId, companyId))
                .workDayCount(getWorkDayCount(accountId, companyId))
                .build();
    }

    private int getWorkDayCount(Long accountId, Long companyId) {
        return enterRecordJpaForWorkerManagementRepository.countTotalWorkDayByAccountIdAndCompanyId(
                accountId, companyId);
    }

    private int getTardyCount(Long accountId, Long companyId) {
        return enterRecordJpaForWorkerManagementRepository.countTardyByAccountIdAndCompanyId(
                accountId, companyId);
    }

    private int getAbsentCount(Long accountId, Long companyId) {
        return absentRecordJpaForWorkerManagementRepository.countAbsentByAccountIdAndCompanyId(
                accountId, companyId);
    }
}
