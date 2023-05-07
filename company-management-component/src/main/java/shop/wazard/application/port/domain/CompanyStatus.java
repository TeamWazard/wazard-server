package shop.wazard.application.port.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyStatus {

    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private final String state;

}
