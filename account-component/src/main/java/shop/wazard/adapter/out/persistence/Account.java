package shop.wazard.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.dto.UpdateCompanyAccountInfoReqDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "accountId")
    private Long id;
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private GenderType gender;
    private LocalDate birth;
    private AccountStatus accountStatus;
    private String roles;

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public Account(String email, String password, String userName, String phoneNumber, GenderType gender, LocalDate birth, String roles) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birth = birth;
        this.roles = roles;
    }

    public void updateCompanyAccountInfo(UpdateCompanyAccountInfoReqDto updateCompanyAccountInfoReqDto) {
        this.email = updateCompanyAccountInfoReqDto.getEmail();
        this.userName = updateCompanyAccountInfoReqDto.getUserName();
        this.phoneNumber = updateCompanyAccountInfoReqDto.getPhoneNumber();
        this.gender = updateCompanyAccountInfoReqDto.getGender();
        this.birth = updateCompanyAccountInfoReqDto.getBirth();
    }
}
