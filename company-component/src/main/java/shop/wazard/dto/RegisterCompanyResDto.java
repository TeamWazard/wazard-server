package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterCompanyResDto {

    private String message;
    private Long companyId;

}
