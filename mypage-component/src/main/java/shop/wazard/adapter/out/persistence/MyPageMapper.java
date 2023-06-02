package shop.wazard.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.CompanyInfoForMyPage;
import shop.wazard.dto.GetPastWorkplaceResDto;
import shop.wazard.entity.company.CompanyJpa;

@Component
class MyPageMapper {

    public List<GetPastWorkplaceResDto> toCompanyInfoDomainList(List<CompanyJpa> companyJpaList) {
        return companyJpaList.stream()
                .map(
                        companyInfo ->
                                GetPastWorkplaceResDto.builder()
                                        .companyId(companyInfo.getId())
                                        .companyName(companyInfo.getCompanyName())
                                        .companyAddress(companyInfo.getCompanyAddress())
                                        .companyContact(companyInfo.getCompanyContact())
                                        .logoImageUrl(companyInfo.getLogoImageUrl())
                                        .build())
                .collect(Collectors.toList());
    }

    public CompanyInfoForMyPage createCompanyInfoForMyPage(CompanyJpa findCompanyJpa) {
        return CompanyInfoForMyPage.builder()
                .companyName(findCompanyJpa.getCompanyName())
                .companyContact(findCompanyJpa.getCompanyContact())
                .companyAddress(findCompanyJpa.getCompanyAddress())
                .logoImageUrl(findCompanyJpa.getLogoImageUrl())
                .salaryDate(findCompanyJpa.getSalaryDate())
                .build();
    }
}
