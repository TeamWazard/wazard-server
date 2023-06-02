package shop.wazard.application.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountForSecurity {

    private String email;
    private String password;
    private String roles;

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }
}
