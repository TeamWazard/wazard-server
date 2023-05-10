package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountForManagement {

    private Long id;
    private String roles;
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
