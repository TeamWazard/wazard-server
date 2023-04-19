package shop.wazard.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum AccountStatus {

    ACTIVE("ACVTIVE"), INACTIVE("INACTIVE");

    private final String accountStatus;

}

