package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.dto.GetPastWorkplaceResDto;
import shop.wazard.entity.company.CompanyJpa;

import java.util.List;
import java.util.stream.Collectors;

@Component
class MyPageMapper {

    public List<GetPastWorkplaceResDto> toCompanyInfoDomainList(List<CompanyJpa> companyJpaList) {
        return companyJpaList.stream()
                .map(companyInfo -> GetPastWorkplaceResDto.builder()
                        .companyId(companyInfo.getId())
                        .companyName(companyInfo.getCompanyName())
                        .companyAddress(companyInfo.getCompanyAddress())
                        .companyContact(companyInfo.getCompanyContact())
                        .logoImageUrl(companyInfo.getLogoImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

}