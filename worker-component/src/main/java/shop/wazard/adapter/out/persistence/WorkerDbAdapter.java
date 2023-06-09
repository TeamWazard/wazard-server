package shop.wazard.adapter.out.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AccountForWorker;
import shop.wazard.application.domain.ContractInfo;
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.application.port.out.AccountForWorkerPort;
import shop.wazard.application.port.out.ContractForWorkerPort;
import shop.wazard.application.port.out.WaitingListForWorkerPort;
import shop.wazard.application.port.out.WorkerPort;
import shop.wazard.dto.GetEarlyContractInfoReqDto;
import shop.wazard.dto.GetEarlyContractInfoResDto;
import shop.wazard.dto.GetMyReplaceRecordReqDto;
import shop.wazard.dto.GetMyReplaceRecordResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.WaitingListJpa;
import shop.wazard.entity.contract.ContractJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;
import shop.wazard.exception.*;
import shop.wazard.util.exception.StatusEnum;

@Repository
@RequiredArgsConstructor
class WorkerDbAdapter
        implements WorkerPort,
                AccountForWorkerPort,
                ContractForWorkerPort,
                WaitingListForWorkerPort {

    private final WorkerMapper workerMapper;
    private final AccountForWorkerMapper accountForWorkerMapper;
    private final ContractInfoForWorkerMapper contractInfoForWorkerMapper;
    private final WaitingListForWorkerMapper waitingListForWorkerMapper;
    private final ReplaceJpaForWorkerRepository replaceJpaForWorkerRepository;
    private final AccountJpaForWorkerRepository accountJpaForWorkerRepository;
    private final CompanyJpaForWorkerRepository companyJpaForWorkerRepository;
    private final ContractJpaForWorkerRepository contractJpaForWorkerRepository;
    private final WaitingListJpaForWorkerRepository waitingListJpaForWorkerRepository;

    @Override
    public AccountForWorker findAccountByEmail(String email) {
        AccountJpa accountJpa =
                accountJpaForWorkerRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountForWorkerMapper.toAccount(accountJpa);
    }

    @Override
    public void saveReplace(String email, ReplaceInfo replaceInfo) {
        AccountJpa accountJpa =
                accountJpaForWorkerRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa =
                companyJpaForWorkerRepository
                        .findById(replaceInfo.getCompanyId())
                        .orElseThrow(
                                () ->
                                        new CompanyNotFoundException(
                                                StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        ReplaceWorkerJpa replaceWorkerJpa =
                workerMapper.saveReplaceInfo(accountJpa, companyJpa, replaceInfo);
        replaceJpaForWorkerRepository.save(replaceWorkerJpa);
    }

    @Override
    public List<GetMyReplaceRecordResDto> getMyReplaceRecord(
            GetMyReplaceRecordReqDto getMyReplaceRecordReqDto, Long companyId) {
        AccountJpa accountJpa =
                accountJpaForWorkerRepository
                        .findByEmail(getMyReplaceRecordReqDto.getEmail())
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        List<ReplaceWorkerJpa> replaceWorkerJpaList =
                replaceJpaForWorkerRepository.findMyReplaceRecord(companyId, accountJpa.getId());
        return workerMapper.toMyReplaceRecord(replaceWorkerJpaList);
    }

    @Override
    public GetEarlyContractInfoResDto getEarlyContractInfo(
            GetEarlyContractInfoReqDto getEarlyContractInfoReqDto) {
        ContractJpa contractJpa =
                contractJpaForWorkerRepository
                        .findByInviteCode(getEarlyContractInfoReqDto.getInvitationCode())
                        .orElseThrow(
                                () ->
                                        new InviteCodeNotFoundException(
                                                StatusEnum.INVITECODE_NOT_FOUND.getMessage()));
        return contractInfoForWorkerMapper.toContractInfo(contractJpa);
    }

    @Override
    public ContractInfo findContractJpaByContractId(Long contractId) {
        ContractJpa contractJpa =
                contractJpaForWorkerRepository
                        .findById(contractId)
                        .orElseThrow(
                                () ->
                                        new ContractNotFoundException(
                                                StatusEnum.CONTRACT_NOT_FOUND.getMessage()));
        return contractInfoForWorkerMapper.contractJpaToContractInfo(contractJpa);
    }

    @Override
    public void modifyContractAgreement(ContractInfo contractInfo) {
        ContractJpa contractJpa =
                contractJpaForWorkerRepository
                        .findById(contractInfo.getContractId())
                        .orElseThrow(
                                () ->
                                        new ContractNotFoundException(
                                                StatusEnum.CONTRACT_NOT_FOUND.getMessage()));
        contractInfoForWorkerMapper.modifyContractAgreement(contractJpa, contractInfo);
    }

    @Override
    public void modifyWaitingListState(ContractInfo contractInfo) {
        WaitingListJpa waitingListJpa =
                waitingListJpaForWorkerRepository
                        .findWaitingListByAccountIdAndCompanyId(
                                contractInfo.getAccountId(), contractInfo.getCompanyId())
                        .orElseThrow(
                                () ->
                                        new WaitingListNotFoundException(
                                                StatusEnum.WAITING_LIST_NOT_FOUND.getMessage()));
        waitingListForWorkerMapper.modifyWaitingListState(waitingListJpa, contractInfo);
    }
}
