package shop.wazard.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Account {

    @Id
    @GeneratedValue
    @Column(name = "accountId")
    private Long id;
    private String email;
    private String password;
    private String userName;
    private String address;
    private String phoneNumber;
    private GenderType genderType;
    private AccountStatus accountStatus;
    private String roles;

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public Account(String email, String password, String userName, String roles) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.roles = roles;
    }
}
