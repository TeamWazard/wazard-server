package shop.wazard.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

}
