package shop.wazard.application.port.out;

import shop.wazard.application.domain.ContractInfo;

public interface ContractForWorkerPort {

    ContractInfo findContractInfoByContractId(Long contractId);

    void checkContractAgreement(ContractInfo contractInfo);
}
