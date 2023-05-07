package shop.wazard.entity.company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CompanyAccountRel")
public class CompanyAccountRelJpa extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyAccountRelation companyAccountRelation;

    @Builder
    public CompanyAccountRelJpa(AccountJpa accountJpa, CompanyJpa companyJpa, CompanyAccountRelation companyAccountRelation) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
        this.companyAccountRelation = companyAccountRelation;
    }

}
