package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.CheckAgreementReqDto;

@Getter
@Builder
public class ContractInfo {

    private Long contractId;
    private boolean contractInfoAgreement;

    public void changeContractAgreementState(CheckAgreementReqDto checkAgreementReqDto) {
        this.contractInfoAgreement = checkAgreementReqDto.isAgreementCheck();
    }
}
