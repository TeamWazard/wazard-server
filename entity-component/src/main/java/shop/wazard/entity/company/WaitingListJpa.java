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
@Table(name = "WaitingList")
public class WaitingListJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "waitingListId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    private AccountJpa accountJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", nullable = false)
    private CompanyJpa companyJpa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaitingStatusJpa waitingStatusJpa;

    @Builder
    public WaitingListJpa(AccountJpa accountJpa, CompanyJpa companyJpa, WaitingStatusJpa waitingStatusJpa) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
        this.waitingStatusJpa = waitingStatusJpa;
    }

    public void updateWaitingStatus(WaitingStatusJpa waitingStatusJpa) {
        this.waitingStatusJpa = waitingStatusJpa;
    }
}
