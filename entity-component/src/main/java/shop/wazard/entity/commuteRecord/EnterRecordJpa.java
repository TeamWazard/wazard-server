package shop.wazard.entity.commuteRecord;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EnterRecord")
public class EnterRecordJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "enterRecordId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpa accountJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private CompanyJpa companyJpa;

    private boolean tardy;

    private LocalDate enterDate;

    private LocalDateTime enterTime;

    @OneToOne(mappedBy = "enterRecordJpa")
    private ExitRecordJpa exitRecordJpa;

    @Builder
    public EnterRecordJpa(AccountJpa accountJpa, CompanyJpa companyJpa, boolean tardy, LocalDateTime enterTime) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
        this.tardy = tardy;
        this.enterTime = enterTime;
    }

}
