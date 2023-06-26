package shop.wazard.application.port.out;

import shop.wazard.application.domain.ContractInfo;

public interface ContractForWorkerPort {

    ContractInfo findContractJpaByContractId(Long contractId);

    void checkContractAgreement(ContractInfo contractInfo);
}
