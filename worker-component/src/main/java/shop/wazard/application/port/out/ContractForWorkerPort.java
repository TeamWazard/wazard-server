package shop.wazard.application.port.out;

import shop.wazard.application.domain.ContractInfo;
import shop.wazard.dto.GetEarlyContractInfoReqDto;
import shop.wazard.dto.GetEarlyContractInfoResDto;

public interface ContractForWorkerPort {

    GetEarlyContractInfoResDto getEarlyContractInfo(
            GetEarlyContractInfoReqDto getEarlyContractInfoReqDto);

    ContractInfo findContractJpaByContractId(Long contractId);

    void modifyContractAgreement(ContractInfo contractInfo);
}
