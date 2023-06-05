package shop.wazard.entity.contract;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ContractPaper")
public class ContractPaperJpa extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "contractPaperId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private CompanyJpa companyJpa;

    private String contractPeriod;

    private String workPlaceAddress;

    private String workDayOfWeek;

    private String workTime;

    private int workPayment;
}
