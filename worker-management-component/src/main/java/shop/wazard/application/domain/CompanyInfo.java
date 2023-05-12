package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyInfo {

    private String companyName;
    private String companyAddress;
    private String companyContact;
    private int salaryDate;
    private String logoImageUrl;

}
