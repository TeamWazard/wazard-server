package shop.wazard.application.port.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.UpdateCompanyReqDto;

@Getter
@Builder
public class CompanyInfo {

    private String companyName;
    private String companyAddress;
    private String companyContact;
    private int salaryDate;
    private String logoImageUrl;

    public void updateCompanyInfo(UpdateCompanyReqDto updateCompanyReqDto) {
        this.companyName = updateCompanyReqDto.getCompanyName();
        this.companyAddress = updateCompanyReqDto.getCompanyAddress();
        this.companyContact = updateCompanyReqDto.getCompanyContact();
        this.salaryDate = updateCompanyReqDto.getSalaryDate();

    }
}
