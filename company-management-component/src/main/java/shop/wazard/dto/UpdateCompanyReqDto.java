package shop.wazard.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCompanyReqDto {

    private Long id;
    private String logoImageUrl;
    private String companyName;
    private String companyAddress;
    private String companyContact;
    private int salaryDate;

}
