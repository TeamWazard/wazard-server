package shop.wazard.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {

    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private final String accountStatus;

}
