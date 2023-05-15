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

    public void checkIsEmployee() {
        if (!isEmployee()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
    }

    private boolean isEmployer() {
        return this.roles.equals("EMPLOYER");
    }

    private boolean isEmployee() {
        return this.roles.equals("EMPLOYEE");
    }

}
