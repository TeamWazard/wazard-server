package shop.wazard.entity.commuteRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommuteTypeJpa {

    ON("ON"), OFF("OFF");

    public final String commuteType;

}
