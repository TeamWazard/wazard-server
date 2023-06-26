package shop.wazard.application.port.out;

import shop.wazard.dto.GetEarlyContractInfoReqDto;
import shop.wazard.dto.GetEarlyContractInfoResDto;
import shop.wazard.application.domain.ContractInfo;

public interface ContractForWorkerPort {

    GetEarlyContractInfoResDto getEarlyContractInfo(
            GetEarlyContractInfoReqDto getEarlyContractInfoReqDto);

    ContractInfo findContractInfoByContractId(Long contractId);

    void checkContractAgreement(ContractInfo contractInfo);
}
