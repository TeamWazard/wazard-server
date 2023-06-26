package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.PatchContractAgreementReqDto;

@Getter
@Builder
public class ContractInfo {

    private Long contractId;
    private boolean contractInfoAgreement;

    public void modifyContractAgreement(PatchContractAgreementReqDto patchContractAgreementReqDto) {
        this.contractInfoAgreement = patchContractAgreementReqDto.isAgreementCheck();
    }
}
