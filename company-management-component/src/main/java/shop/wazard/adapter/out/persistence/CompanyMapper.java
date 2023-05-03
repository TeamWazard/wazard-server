package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;

@Component
class CompanyMapper {
    public CompanyJpa toCompanyEntity(Company company) {
        return CompanyJpa.builder()
                .companyName(company.getCompanyInfo().getCompanyName())
                .companyAddress(company.getCompanyInfo().getCompanyAddress())
                .companyContact(company.getCompanyInfo().getCompanyContact())
                .salaryDate(company.getCompanyInfo().getSalaryDate())
                .build();
    }

    public Account toAccountDomain(AccountJpa accountJpa) {
        return Account.builder()
                .id(accountJpa.getId())
                .roles(accountJpa.getRoles())
                .email(accountJpa.getEmail())
                .userName(accountJpa.getUserName())
                .build();
    }

    public AccountJpa toAccountEntity(Account account) {
        return AccountJpa.builder()
                .email(account.getEmail())
                .roles(account.getRoles())
                .userName(account.getUserName())
                .build();
    }
}
