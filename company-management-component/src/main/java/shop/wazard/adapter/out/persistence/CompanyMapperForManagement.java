package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.port.domain.CompanyForManagement;
import shop.wazard.application.port.domain.CompanyInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;

@Component
class CompanyMapperForManagement {

    public CompanyJpa toCompanyJpa(CompanyForManagement companyForManagement) {
        return CompanyJpa.builder()
                .companyName(companyForManagement.getCompanyInfo().getCompanyName())
                .companyAddress(companyForManagement.getCompanyInfo().getCompanyAddress())
                .companyContact(companyForManagement.getCompanyInfo().getCompanyContact())
                .salaryDate(companyForManagement.getCompanyInfo().getSalaryDate())
                .logoImageUrl(companyForManagement.getCompanyInfo().getLogoImageUrl())
                .build();
    }

    public CompanyAccountRelJpa saveRelationInfo(AccountJpa accountJpa, CompanyJpa companyJpa) {
        return CompanyAccountRelJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
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

    public void deleteCompany(CompanyJpa companyJpa) {
        companyJpa.delete();
    }

}