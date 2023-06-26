package shop.wazard.dto;

import javax.validation.constraints.Positive;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchContractAgreementReqDto {

    @Positive private Long contractId;

    private boolean agreementCheck;
}
