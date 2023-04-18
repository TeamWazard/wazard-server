package shop.wazard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatusDomain {

    ACTIVE("ACVTIVE"), INACTIVE("INACTIVE");

    private final String accountStatus;

}
