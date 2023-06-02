package shop.wazard.entity.worker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ReplaceWorker")
public class ReplaceWorkerJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "replaceWorkerId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    private AccountJpa accountJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", nullable = false)
    private CompanyJpa companyJpa;

    @Column(nullable = false)
    private String replaceWorkerName;

    @Column(nullable = false)
    private LocalDate replaceDate;

    @Column(nullable = false)
    private LocalDateTime enterTime;

    @Column(nullable = false)
    private LocalDateTime exitTime;

    @Builder
    public ReplaceWorkerJpa(
            AccountJpa accountJpa,
            CompanyJpa companyJpa,
            String replaceWorkerName,
            LocalDate replaceDate,
            LocalDateTime enterTime,
            LocalDateTime exitTime) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
        this.replaceWorkerName = replaceWorkerName;
        this.replaceDate = replaceDate;
        this.enterTime = enterTime;
        this.exitTime = exitTime;
    }
}
