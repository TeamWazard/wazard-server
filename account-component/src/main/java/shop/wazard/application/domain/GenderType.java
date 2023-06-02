package shop.wazard.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String gender;
}
