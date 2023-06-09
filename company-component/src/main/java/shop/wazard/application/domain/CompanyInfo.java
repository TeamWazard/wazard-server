package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.UpdateCompanyInfoReqDto;

@Getter
@Builder
public class CompanyInfo {

    private String companyName;
    private int zipCode;
    private String companyAddress;
    private String companyDetailAddress;
    private String companyContact;
    private int salaryDate;
    private String businessType;
    private String logoImageUrl;

    public void updateCompanyInfo(UpdateCompanyInfoReqDto updateCompanyInfoReqDto) {
        this.companyName = updateCompanyInfoReqDto.getCompanyName();
        this.companyAddress = updateCompanyInfoReqDto.getCompanyAddress();
        this.companyContact = updateCompanyInfoReqDto.getCompanyContact();
        this.salaryDate = updateCompanyInfoReqDto.getSalaryDate();
        this.logoImageUrl = updateCompanyInfoReqDto.getLogoImageUrl();
    }
}
