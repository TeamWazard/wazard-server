package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.ModifyContractAgreementReqDto;

@Getter
@Builder
public class ContractInfo {

    private Long contractId;
    private Long accountId;
    private Long companyId;
    private boolean contractInfoAgreement;

    public void modifyContractAgreement(
            ModifyContractAgreementReqDto modifyContractAgreementReqDto) {
        this.contractInfoAgreement = modifyContractAgreementReqDto.isAgreementCheck();
    }
}
