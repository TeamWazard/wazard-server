package shop.wazard.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCompanyInfoReqDto {


    private Long companyId;
    private String logoImageUrl;
    private String companyName;
    private String companyAddress;
    private String companyContact;
    private int salaryDate;

}
