package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.ContractInfo;
import shop.wazard.dto.GetEarlyContractInfoResDto;
import shop.wazard.entity.contract.ContractJpa;

@Component
class ContractInfoForWorkerMapper {

    public ContractInfo contractJpaToContractInfo(ContractJpa contractJpa) {
        return ContractInfo.builder()
                .contractId(contractJpa.getId())
                .accountId(contractJpa.getAccountJpa().getId())
                .companyId(contractJpa.getCompanyJpa().getId())
                .contractInfoAgreement(contractJpa.isContractInfoAgreement())
                .build();
    }

    public void modifyContractAgreement(ContractJpa contractJpa, ContractInfo contractInfo) {
        contractJpa.modifyContractAgreement(contractInfo.isContractInfoAgreement());
    }

    public GetEarlyContractInfoResDto toContractInfo(ContractJpa contractJpa) {
        return GetEarlyContractInfoResDto.builder()
                .userName(contractJpa.getAccountJpa().getUserName())
                .companyName(contractJpa.getCompanyJpa().getCompanyName())
                .address(contractJpa.getCompanyJpa().getCompanyAddress())
                .startDate(contractJpa.getStartPeriod())
                .endDate(contractJpa.getEndPeriod())
                .workingTime(contractJpa.getWorkTime())
                .wage(contractJpa.getWage())
                .build();
    }

}
