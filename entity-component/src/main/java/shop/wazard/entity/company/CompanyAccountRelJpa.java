package shop.wazard.entity.company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.account.AccountJpa;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CompanyAccountRel")
public class CompanyAccountRelJpa {
    @Id
    @GeneratedValue
    @Column(name = "companyAccountId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpa accountJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private CompanyJpa companyJpa;

    @Builder
    public CompanyAccountRelJpa(AccountJpa accountJpa, CompanyJpa companyJpa) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
    }

}
