package shop.wazard.entity.company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import shop.wazard.entity.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Company")
public class CompanyJpa extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "companyId")
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String companyAddress;

    @Column(nullable = false)
    private String companyContact;

    // 월급날 (ex: 11일)
    private int salaryDate;

    @OneToOne
    private LogoImageJpa logoImageJpa;

    @OneToMany(mappedBy = "companyJpa")
    private List<CompanyAccountRelJpa> companyAccountRelJpaList = new ArrayList<>();

    @Builder
    public CompanyJpa(String companyName, String companyAddress, String companyContact, int salaryDate, LogoImageJpa logoImageJpa, List<CompanyAccountRelJpa> companyAccountRelJpaList) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyContact = companyContact;
        this.salaryDate = salaryDate;
        this.logoImageJpa = logoImageJpa;
        this.companyAccountRelJpaList = companyAccountRelJpaList;
    }
}
