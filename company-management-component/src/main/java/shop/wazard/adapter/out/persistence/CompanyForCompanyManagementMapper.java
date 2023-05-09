package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.CompanyForManagement;
import shop.wazard.application.domain.CompanyInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RelationTypeJpa;
import shop.wazard.entity.company.RosterJpa;

import java.util.List;
import java.util.stream.Collectors;

@Component
class CompanyForCompanyManagementMapper {

    public CompanyJpa toCompanyJpa(CompanyForManagement companyForManagement) {
        return CompanyJpa.builder()
                .companyName(companyForManagement.getCompanyInfo().getCompanyName())
                .companyAddress(companyForManagement.getCompanyInfo().getCompanyAddress())
                .companyContact(companyForManagement.getCompanyInfo().getCompanyContact())
                .salaryDate(companyForManagement.getCompanyInfo().getSalaryDate())
                .logoImageUrl(companyForManagement.getCompanyInfo().getLogoImageUrl())
                .build();
    }

    public RosterJpa saveRelationInfo(AccountJpa accountJpa, CompanyJpa companyJpa, RelationTypeJpa relationTypeJpa) {
        return RosterJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .relationTypeJpa(relationTypeJpa)
                .build();
    }

    public void updateCompanyInfo(CompanyJpa companyJpa, CompanyForManagement companyForManagement) {
        companyJpa.updateCompanyInfo(
                companyForManagement.getCompanyInfo().getCompanyName(),
                companyForManagement.getCompanyInfo().getCompanyAddress(),
                companyForManagement.getCompanyInfo().getCompanyContact(),
                companyForManagement.getCompanyInfo().getSalaryDate(),
                companyForManagement.getCompanyInfo().getLogoImageUrl()
        );
    }

    public CompanyForManagement toCompanyDomain(CompanyJpa companyJpa) {
        return CompanyForManagement.builder()
                .id(companyJpa.getId())
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName(companyJpa.getCompanyName())
                                .companyAddress(companyJpa.getCompanyAddress())
                                .companyContact(companyJpa.getCompanyContact())
                                .salaryDate(companyJpa.getSalaryDate())
                                .logoImageUrl(companyJpa.getLogoImageUrl())
                                .build()
                )
                .build();
    }

    public List<CompanyForManagement> toOwnedCompanyList(List<CompanyJpa> ownedCompanyJpaList) {
        return ownedCompanyJpaList.stream()
                .map(companyJpa -> CompanyForManagement.builder()
                        .id(companyJpa.getId())
                        .companyInfo(CompanyInfo.builder()
                                .companyName(companyJpa.getCompanyName())
                                .companyAddress(companyJpa.getCompanyAddress())
                                .companyContact(companyJpa.getCompanyContact())
                                .logoImageUrl(companyJpa.getLogoImageUrl())
                                .salaryDate(companyJpa.getSalaryDate())
                                .build())
                        .build()
                ).collect(Collectors.toList());
    }

}