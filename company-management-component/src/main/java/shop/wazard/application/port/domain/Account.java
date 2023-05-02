package shop.wazard.application.port.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Account {

    private Long id;
    private String roles;
    private AccountStatus accountStatus;
    private String email;
    private String userName;

}
