package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.domain.CompanyInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyAccountRelJpa;
import shop.wazard.entity.company.CompanyJpa;

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

    public CompanyAccountRelJpa saveRelationInfo(AccountJpa accountJpa, CompanyJpa companyJpa) {
        return CompanyAccountRelJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .build();
    }

    public void updateCompanyInfo(CompanyJpa companyJpa, Company company) {
        companyJpa.updateCompanyInfo(
                company.getCompanyInfo().getCompanyName(),
                company.getCompanyInfo().getCompanyAddress(),
                company.getCompanyInfo().getCompanyContact(),
                company.getCompanyInfo().getSalaryDate(),
                company.getCompanyInfo().getLogoImageUrl()
        );
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
                                .build()
                )
                .build();
    }

}