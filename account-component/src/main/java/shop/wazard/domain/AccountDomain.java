package shop.wazard.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
public class AccountDomain {

    private Long id;
    private String email;
    private String password;
    private String userName;
    private String address;
    private String phoneNumber;
    private GenderTypeDomain genderTypeDomain;
    private AccountStatusDomain accountStatusDomain;
    private String roles;

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

}
