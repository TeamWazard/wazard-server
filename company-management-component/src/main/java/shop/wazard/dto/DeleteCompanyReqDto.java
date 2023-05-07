package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteCompanyReqDto {

    private String email;
    private Long companyId;

}
