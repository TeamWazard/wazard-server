package shop.wazard.entity.company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
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
    private int zipCode;

    @Column(nullable = false)
    private String companyAddress;

    @Column(nullable = false)
    private String companyDetailAddress;

    @Column(nullable = false)
    private String businessType;

    @Column(nullable = false)
    private String companyContact;

    // 월급날 (ex: 11일)
    private int salaryDate;

    @Column(nullable = false)
    private String logoImageUrl;

    @OneToMany(mappedBy = "companyJpa")
    private List<RosterJpa> rosterJpaList = new ArrayList<>();

    @OneToMany(mappedBy = "companyJpa")
    private List<EnterRecordJpa> enterRecordJpa = new ArrayList<>();


    @Builder
    public CompanyJpa(String companyName, int zipCode, String companyAddress, String companyDetailAddress, String companyContact, String businessType, int salaryDate, String logoImageUrl) {
        this.companyName = companyName;
        this.zipCode = zipCode;
        this.companyAddress = companyAddress;
        this.companyDetailAddress = companyDetailAddress;
        this.companyContact = companyContact;
        this.businessType = businessType;
        this.salaryDate = salaryDate;
        this.logoImageUrl = logoImageUrl;
    }

    public void updateCompanyInfo(String companyName, String companyAddress, String companyContact, int salaryDate, String logoImageUrl) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyContact = companyContact;
        this.salaryDate = salaryDate;
        this.logoImageUrl = logoImageUrl;
    }

    public void delete() {
        this.baseStatusJpa = BaseStatusJpa.INACTIVE;
    }

}
