package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.ContractInfo;
import shop.wazard.entity.contract.ContractJpa;

@Component
class ContractInfoForWorkerMapper {

    public ContractInfo contractJpaToContractInfo(ContractJpa contractJpa) {
        return ContractInfo.builder()
                .contractId(contractJpa.getId())
                .contractInfoAgreement(contractJpa.isContractInfoAgreement())
                .build();
    }

    public void modifyContractAgreement(ContractJpa contractJpa, ContractInfo contractInfo) {
        contractJpa.modifyContractAgreement(contractInfo.isContractInfoAgreement());
    }
}
