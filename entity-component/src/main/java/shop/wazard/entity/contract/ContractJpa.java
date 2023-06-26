package shop.wazard.entity.contract;

import java.time.LocalDate;
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
@Table(name = "Contract")
public class ContractJpa extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "contractId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountJpa accountJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private CompanyJpa companyJpa;

    @Column(nullable = false)
    private String inviteCode;

    @Column(nullable = false)
    private LocalDate startPeriod;

    @Column(nullable = false)
    private LocalDate endPeriod;

    @Column(nullable = false)
    private String workPlaceAddress;

    @Column(nullable = false)
    private String workTime;

    @Column(nullable = false)
    private int wage; // 시급

    @Column(nullable = false)
    private boolean contractInfoAgreement; // 계약정보 조회 동의 여부

    @Builder
    public ContractJpa(
            AccountJpa accountJpa,
            CompanyJpa companyJpa,
            String inviteCode,
            LocalDate startPeriod,
            LocalDate endPeriod,
            String workPlaceAddress,
            String workTime,
            int wage,
            boolean contractInfoAgreement) {
        this.accountJpa = accountJpa;
        this.companyJpa = companyJpa;
        this.inviteCode = inviteCode;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.workPlaceAddress = workPlaceAddress;
        this.workTime = workTime;
        this.wage = wage;
        this.contractInfoAgreement = contractInfoAgreement;
    }
}
