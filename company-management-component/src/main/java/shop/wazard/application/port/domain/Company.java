package shop.wazard.application.port.domain;

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
                .companyInfo(CompanyInfo.builder()
                        .companyName(registerCompanyReqDto.getCompanyName())
                        .companyAddress(registerCompanyReqDto.getCompanyAddress())
                        .companyContact(registerCompanyReqDto.getCompanyContact())
                        .build())
                .build();
    }

}
