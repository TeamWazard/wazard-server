package shop.wazard.entity.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderTypeJpa {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String gender;
}
