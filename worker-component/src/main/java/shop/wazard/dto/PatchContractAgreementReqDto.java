package shop.wazard.dto;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Positive;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchContractAgreementReqDto {

    @Positive private Long contractId;

    @AssertFalse(message = "초기 계약 정보에 대한 동의 여부는 false입니다.")
    private boolean agreementCheck;
}
