package shop.wazard.entity.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatusJpa {

    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private final String accountStatus;

}

