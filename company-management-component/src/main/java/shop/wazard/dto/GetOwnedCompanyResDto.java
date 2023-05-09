package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOwnedCompanyResDto {

    private Long companyId;
    private String companyName;
    private String companyAddress;
    private String companyPhoneNumber;
    private String logoImageUrl;
    private int salaryDate;

}
