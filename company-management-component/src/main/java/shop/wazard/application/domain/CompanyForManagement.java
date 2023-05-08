package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.RegisterCompanyReqDto;


@Getter
@Builder
public class CompanyForManagement {

    private Long id;
    private CompanyInfo companyInfo;

    public static CompanyForManagement createCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        return CompanyForManagement.builder()
                .companyInfo(CompanyInfo.builder()
                        .companyName(registerCompanyReqDto.getCompanyName())
                        .companyAddress(registerCompanyReqDto.getCompanyAddress())
                        .companyContact(registerCompanyReqDto.getCompanyContact())
                        .salaryDate(registerCompanyReqDto.getSalaryDate())
                        .logoImageUrl(registerCompanyReqDto.getLogoImageUrl())
                        .build())
                .build();
    }

}