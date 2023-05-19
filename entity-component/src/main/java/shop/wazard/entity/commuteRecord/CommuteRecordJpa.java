package shop.wazard.entity.commuteRecord;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CommuteRecord")
public class CommuteRecordJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "commuteRecordId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpa accountJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private CompanyJpa companyJpa;

    @OneToOne
    @JoinColumn(name = "exitRecordId")
    private ExitRecordJpa exitRecordJpa;

    private boolean tardy;

    private LocalDateTime commuteTime;

    @Builder
    public CommuteRecordJpa(AccountJpa accountJpa, CompanyJpa companyJpa, boolean tardy, LocalDateTime commuteTime) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
        this.tardy = tardy;
        this.commuteTime = commuteTime;
    }

}
