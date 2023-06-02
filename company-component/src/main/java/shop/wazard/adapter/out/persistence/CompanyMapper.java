package shop.wazard.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.Company;
import shop.wazard.application.domain.CompanyInfo;
import shop.wazard.dto.GetBelongedCompanyResDto;
import shop.wazard.dto.GetOwnedCompanyResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RosterJpa;
import shop.wazard.entity.company.RosterTypeJpa;

@Component
class CompanyMapper {

    public CompanyJpa toCompanyJpa(Company company) {
        return CompanyJpa.builder()
                .companyName(company.getCompanyInfo().getCompanyName())
                .companyAddress(company.getCompanyInfo().getCompanyAddress())
                .companyContact(company.getCompanyInfo().getCompanyContact())
                .salaryDate(company.getCompanyInfo().getSalaryDate())
                .logoImageUrl(company.getCompanyInfo().getLogoImageUrl())
                .build();
    }

    public RosterJpa saveRelationInfo(
            AccountJpa accountJpa, CompanyJpa companyJpa, RosterTypeJpa rosterTypeJpa) {
        return RosterJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .rosterTypeJpa(rosterTypeJpa)
                .build();
    }

    public void updateCompanyInfo(CompanyJpa companyJpa, Company company) {
        companyJpa.updateCompanyInfo(
                company.getCompanyInfo().getCompanyName(),
                company.getCompanyInfo().getCompanyAddress(),
                company.getCompanyInfo().getCompanyContact(),
                company.getCompanyInfo().getSalaryDate(),
                company.getCompanyInfo().getLogoImageUrl());
    }

    public Company toCompanyDomain(CompanyJpa companyJpa) {
        return Company.builder()
                .id(companyJpa.getId())
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName(companyJpa.getCompanyName())
                                .companyAddress(companyJpa.getCompanyAddress())
                                .companyContact(companyJpa.getCompanyContact())
                                .salaryDate(companyJpa.getSalaryDate())
                                .logoImageUrl(companyJpa.getLogoImageUrl())
                                .build())
                .build();
    }

    public List<GetOwnedCompanyResDto> toOwnedCompanyList(List<CompanyJpa> ownedCompanyJpaList) {
        return ownedCompanyJpaList.stream()
                .map(
                        companyJpa ->
                                GetOwnedCompanyResDto.builder()
                                        .companyId(companyJpa.getId())
                                        .companyName(companyJpa.getCompanyName())
                                        .companyAddress(companyJpa.getCompanyAddress())
                                        .companyContact(companyJpa.getCompanyContact())
                                        .logoImageUrl(companyJpa.getLogoImageUrl())
                                        .salaryDate(companyJpa.getSalaryDate())
                                        .build())
                .collect(Collectors.toList());
    }

    public List<GetBelongedCompanyResDto> toBelongedCompanyList(
            List<CompanyJpa> belongedCompanyJpaList) {
        return belongedCompanyJpaList.stream()
                .map(
                        companyJpa ->
                                GetBelongedCompanyResDto.builder()
                                        .companyId(companyJpa.getId())
                                        .companyName(companyJpa.getCompanyName())
                                        .companyAddress(companyJpa.getCompanyAddress())
                                        .companyContact(companyJpa.getCompanyContact())
                                        .logoImageUrl(companyJpa.getLogoImageUrl())
                                        .salaryDate(companyJpa.getSalaryDate())
                                        .build())
                .collect(Collectors.toList());
    }
}
