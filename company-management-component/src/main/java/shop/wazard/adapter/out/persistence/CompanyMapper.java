package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.port.domain.Company;
import shop.wazard.entity.company.CompanyJpa;

@Component
class CompanyMapper {

    public CompanyJpa toCompanyJpa(Company company) {
        return CompanyJpa.builder()
                .companyName(company.getCompanyInfo().getCompanyName())
                .companyAddress(company.getCompanyInfo().getCompanyAddress())
                .companyContact(company.getCompanyInfo().getCompanyContact())
                .salaryDate(company.getCompanyInfo().getSalaryDate())
                .build();
    }

}
