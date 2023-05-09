package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOwnedCompanyListResDto {

    private String companyName;
    private String companyAddress;
    private String companyPhoneNumber;
    private String LogoImageUrl;
    private int salaryDate;

}
