package shop.wazard.entity.commuteRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommuteTypeJpa {

    START("START"), END("END");

    public final String commuteType;
}
