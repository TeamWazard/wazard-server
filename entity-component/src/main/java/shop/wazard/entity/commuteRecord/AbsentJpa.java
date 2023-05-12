package shop.wazard.entity.commuteRecord;

import lombok.AccessLevel;
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
    @JoinColumn(name = "accountId")
    @Column(nullable = false)
    private AccountJpa accountJpa;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private CompanyJpa companyJpa;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate absentDate;

}
