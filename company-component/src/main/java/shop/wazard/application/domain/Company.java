package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.RegisterCompanyReqDto;

@Getter
@Builder
public class Company {

    private Long id;
    private CompanyInfo companyInfo;

    public static Company createCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        return Company.builder()
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName(registerCompanyReqDto.getCompanyName())
                                .zipCode(registerCompanyReqDto.getZipCode())
                                .companyAddress(registerCompanyReqDto.getCompanyAddress())
                                .companyDetailAddress(
                                        registerCompanyReqDto.getCompanyDetailAddress())
                                .companyContact(registerCompanyReqDto.getCompanyContact())
                                .salaryDate(registerCompanyReqDto.getSalaryDate())
                                .businessType(registerCompanyReqDto.getBusinessType())
                                .logoImageUrl(registerCompanyReqDto.getLogoImageUrl())
                                .build())
                .build();
    }
}
