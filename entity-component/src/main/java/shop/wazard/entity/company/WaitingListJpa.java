package shop.wazard.entity.company;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.account.AccountJpa;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "WaitingList")
public class WaitingListJpa {

    @Id
    @GeneratedValue
    @Column(name = "waitingListId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpa accountJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private CompanyJpa companyJpa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaitingStatusJpa waitingStatusJpa;

}
