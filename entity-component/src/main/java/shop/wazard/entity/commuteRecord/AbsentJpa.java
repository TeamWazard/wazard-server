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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Absent")
public class AbsentJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "absentId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    private AccountJpa accountJpa;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", nullable = false)
    private CompanyJpa companyJpa;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate absentDate;

    @Builder
    public AbsentJpa(AccountJpa accountJpa, CompanyJpa companyJpa, LocalDate absentDate) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
        this.absentDate = absentDate;
    }

}
