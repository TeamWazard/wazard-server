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
@Table(name = "Roster")
public class RosterJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "rosterId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpa accountJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private CompanyJpa companyJpa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RosterTypeJpa rosterTypeJpa;

    @Builder
    public RosterJpa(AccountJpa accountJpa, CompanyJpa companyJpa, RosterTypeJpa rosterTypeJpa) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
        this.rosterTypeJpa = rosterTypeJpa;
    }

    public void updateRosterStateForExile(BaseStatusJpa baseStatusJpa) {
        this.baseStatusJpa = baseStatusJpa;
    }

}
