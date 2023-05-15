package shop.wazard.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommuteType {

    ON("ON"), OFF("OFF");

    public final String commuteType;

}
