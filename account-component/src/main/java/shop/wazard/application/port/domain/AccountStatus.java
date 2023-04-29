package shop.wazard.application.port.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {

    ACTIVE("ACVTIVE"), INACTIVE("INACTIVE");

    private final String accountStatus;

}
