package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBelongedCompanyResDto {

    private Long companyId;
    private String companyName;
    private String companyAddress;
    private String companyContact;
    private String logoImageUrl;
    private int salaryDate;

}
