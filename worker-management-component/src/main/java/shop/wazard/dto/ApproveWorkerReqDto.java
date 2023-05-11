package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApproveWorkerReqDto {

    private Long accountId;
    private Long companyId;
    private String email;

}
