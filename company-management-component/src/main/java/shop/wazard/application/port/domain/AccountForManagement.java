package shop.wazard.application.port.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountForManagement {

    private Long id;
    private String roles;
    private AccountStatus accountStatus;
    private String email;
    private String userName;

    public Boolean isEmployer() {
        if (this.roles.equals("EMPLOYER")) {
            return true;
        } else {
            return false;
        }
    }

}
