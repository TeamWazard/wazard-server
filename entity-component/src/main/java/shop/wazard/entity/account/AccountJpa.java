package shop.wazard.entity.account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.RosterJpa;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Account")
public class AccountJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "accountId")
    private Long id;

    private String email;

    private String password;

    private String userName;

    private String phoneNumber;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private GenderTypeJpa gender;

    private String roles;

    @OneToMany(mappedBy = "accountJpa")
    private List<RosterJpa> rosterJpaList = new ArrayList<>();

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public AccountJpa(
            String email,
            String password,
            String userName,
            String phoneNumber,
            String gender,
            LocalDate birth,
            String roles,
            BaseStatusJpa baseStatusJpa,
            List<RosterJpa> rosterJpaList) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.gender = GenderTypeJpa.valueOf(gender);
        this.birth = birth;
        this.roles = roles;
        this.baseStatusJpa = baseStatusJpa;
        this.rosterJpaList = rosterJpaList;
    }

    public void updateMyProfile(
            String userName, String phoneNumber, String genderType, LocalDate birth) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.gender = GenderTypeJpa.valueOf(genderType);
        this.birth = birth;
    }
}
