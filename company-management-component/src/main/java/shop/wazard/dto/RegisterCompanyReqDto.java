package shop.wazard.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterCompanyReqDto {

    private String email;
    private String logoImageUrl;
    private String companyName;
    private String companyAddress;
    private String companyContact;
    private int salaryDate;

}
