package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AccountForWorker;
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.application.port.out.AccountForWorkerPort;
import shop.wazard.application.port.out.WorkerPort;
import shop.wazard.dto.GetMyReplaceRecordReqDto;
import shop.wazard.dto.GetMyReplaceRecordResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import java.util.List;

@Repository
@RequiredArgsConstructor
class WorkerDbAdapter implements WorkerPort, AccountForWorkerPort {

    private final WorkerMapper workerMapper;
    private final AccountForWorkerMapper accountForWorkerMapper;
    private final ReplaceJpaForWorkerRepository replaceJpaForWorkerRepository;
    private final AccountJpaForWorkerRepository accountJpaForWorkerRepository;
    private final CompanyJpaForWorkerRepository companyJpaForWorkerRepository;


    @Override
    public AccountForWorker findAccountByEmail(String email) {
        AccountJpa accountJpa = accountJpaForWorkerRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountForWorkerMapper.toAccount(accountJpa);
    }

    @Override
    public void saveReplace(String email, ReplaceInfo replaceInfo) {
        AccountJpa accountJpa = accountJpaForWorkerRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa = companyJpaForWorkerRepository.findById(replaceInfo.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        ReplaceWorkerJpa replaceWorkerJpa = workerMapper.saveReplaceInfo(accountJpa, companyJpa, replaceInfo);
        replaceJpaForWorkerRepository.save(replaceWorkerJpa);
    }

    @Override
    public List<GetMyReplaceRecordResDto> getMyReplaceRecord(GetMyReplaceRecordReqDto getMyReplaceRecordReqDto) {
        AccountJpa accountJpa = accountJpaForWorkerRepository.findByEmail(getMyReplaceRecordReqDto.getEmail())
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        List<ReplaceWorkerJpa> replaceWorkerJpaList = replaceJpaForWorkerRepository.findMyReplaceRecord(getMyReplaceRecordReqDto.getCompanyId(), accountJpa);
        return workerMapper.toMyReplaceRecord(replaceWorkerJpaList);
    }

}
