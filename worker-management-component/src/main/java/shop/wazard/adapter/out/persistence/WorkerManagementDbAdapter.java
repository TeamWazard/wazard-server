package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AccountForWorkerManagement;
import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.port.out.AccountForWorkerManagementPort;
import shop.wazard.application.port.out.RosterForWorkerManagementPort;
import shop.wazard.application.port.out.WaitingListForWorkerManagementPort;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RosterJpa;
import shop.wazard.entity.company.RosterTypeJpa;
import shop.wazard.entity.company.WaitingListJpa;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.exception.WorkerNotFoundInWaitingListException;
import shop.wazard.util.exception.StatusEnum;

@Repository
@RequiredArgsConstructor
class WorkerManagementDbAdapter implements AccountForWorkerManagementPort, RosterForWorkerManagementPort, WaitingListForWorkerManagementPort {

    private final WorkerManagementMapper workerForManagementMapper;
    private final AccountForWorkerManagementMapper accountForWorkerManagementMapper;
    private final AccountForWorkerManagementRepository accountForWorkerManagementRepository;
    private final CompanyForWorkerManagementRepository companyForWorkerManagementRepository;
    private final RosterForWorkerManagementRepository rosterForWorkerManagementRepository;
    private final WaitingListForWorkerManagementRepository waitingListForWorkerManagementRepository;

    @Override
    public void joinWorker(RosterForWorkerManagement rosterForWorkerManagement) {
        AccountJpa accountJpa = accountForWorkerManagementRepository.findById(rosterForWorkerManagement.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa = companyForWorkerManagementRepository.findById(rosterForWorkerManagement.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        RosterJpa rosterJpa = RosterJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .rosterTypeJpa(RosterTypeJpa.valueOf(rosterForWorkerManagement.getRelationType().getType()))
                .build();
        rosterForWorkerManagementRepository.save(rosterJpa);
    }

    @Override
    public WaitingInfo findWaitingInfo(Long waitingListId) {
        WaitingListJpa waitingListJpa = waitingListForWorkerManagementRepository.findById(waitingListId)
                .orElseThrow(() -> new WorkerNotFoundInWaitingListException(StatusEnum.WORKER_NOT_FOUND_IN_WAITING_LIST.getMessage()));
        return workerForManagementMapper.toWaitingInfoDomain(waitingListJpa);
    }

    @Override
    public AccountForWorkerManagement findAccountByEmail(String email) {
        AccountJpa accountJpa = accountForWorkerManagementRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountForWorkerManagementMapper.toAccountDomain(accountJpa);
    }

    @Override
    public void updateWaitingStatus(WaitingInfo waitingInfo) {
        AccountJpa accountJpa = accountForWorkerManagementRepository.findById(waitingInfo.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa = companyForWorkerManagementRepository.findById(waitingInfo.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        WaitingListJpa waitingListJpa = waitingListForWorkerManagementRepository.findByAccountJpaAndCompanyJpa(accountJpa, companyJpa)
                .orElseThrow(() -> new WorkerNotFoundInWaitingListException(StatusEnum.WORKER_NOT_FOUND_IN_WAITING_LIST.getMessage()));
        waitingListJpa.updateWaitingStatus();
    }
}
