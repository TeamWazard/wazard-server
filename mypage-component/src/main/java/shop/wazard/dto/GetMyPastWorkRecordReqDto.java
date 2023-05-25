package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMyPastWorkRecordReqDto {

    private String email;
    private Long accountId;
    private Long companyId;

}
