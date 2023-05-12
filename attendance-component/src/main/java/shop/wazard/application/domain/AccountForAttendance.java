package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.exception.NotAuthorizedException;
import shop.wazard.util.exception.StatusEnum;

@Getter
@Builder
public class AccountForAttendance {

    private Long id;
    private String roles;

    public void checkIsEmployer() {
        if (!isEmployer()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
    }

    private boolean isEmployer() {
        if (this.roles.equals("EMPLOYER")) {
            return true;
        } else {
            return false;
        }
    }

}
