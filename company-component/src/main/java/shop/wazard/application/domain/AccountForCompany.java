package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.exception.NotAuthorizedException;
import shop.wazard.util.exception.StatusEnum;

@Getter
@Builder
public class AccountForCompany {

    private Long id;
    private String roles;
    private String email;
    private String userName;

    public void checkIsEmployer() {
        if (!this.roles.equals("EMPLOYER")) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
    }

    public void checkIsEmployee() {
        if (!this.roles.equals("EMPLOYEE")) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
    }
}
