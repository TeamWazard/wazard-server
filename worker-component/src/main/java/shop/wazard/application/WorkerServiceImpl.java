package shop.wazard.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForWorker;
import shop.wazard.application.domain.ContractInfo;
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.application.port.in.WorkerService;
import shop.wazard.application.port.out.AccountForWorkerPort;
import shop.wazard.application.port.out.ContractForWorkerPort;
import shop.wazard.application.port.out.WorkerPort;
import shop.wazard.dto.*;

@Service
@Transactional
@RequiredArgsConstructor
class WorkerServiceImpl implements WorkerService {

    private final WorkerPort workerPort;
    private final AccountForWorkerPort accountForWorkerPort;
    private final ContractForWorkerPort contractForWorkerPort;

    @Override
    public RegisterReplaceResDto registerReplace(RegisterReplaceReqDto registerReplaceReqDto) {
        AccountForWorker accountForWorker =
                accountForWorkerPort.findAccountByEmail(registerReplaceReqDto.getEmail());
        accountForWorker.checkIsEmployee();
        workerPort.saveReplace(
                accountForWorker.getEmail(), ReplaceInfo.createReplace(registerReplaceReqDto));
        return RegisterReplaceResDto.builder().message("대타 등록이 완료되었습니다.").build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetMyReplaceRecordResDto> getMyReplaceRecord(
            GetMyReplaceRecordReqDto getMyReplaceRecordReqDto, Long companyId) {
        AccountForWorker accountForWorker =
                accountForWorkerPort.findAccountByEmail(getMyReplaceRecordReqDto.getEmail());
        accountForWorker.checkIsEmployee();
        return workerPort.getMyReplaceRecord(getMyReplaceRecordReqDto, companyId);
    }

    @Override
    public PatchContractAgreementResDto modifyContractAgreement(
            PatchContractAgreementReqDto patchContractAgreementReqDto) {
        ContractInfo contractInfo =
                contractForWorkerPort.findContractJpaByContractId(
                        patchContractAgreementReqDto.getContractId());
        contractInfo.modifyContractAgreement(patchContractAgreementReqDto);
        contractForWorkerPort.modifyContractAgreement(contractInfo);
        return PatchContractAgreementResDto.builder()
                .message(patchContractAgreementReqDto.isAgreementCheck() ? "동의하였습니다." : "비동의하였습니다.")
                .build();
    }
}
