package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountForWorkerManagement {

    private Long id;
    private String roles;

    public boolean isEmployer() {
        if (this.roles.equals("EMPLOYER")) {
            return true;
        } else {
            return false;
        }
    }

}
