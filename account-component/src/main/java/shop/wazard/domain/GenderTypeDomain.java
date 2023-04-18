package shop.wazard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderTypeDomain {

    MALE("MALE"), FEMALE("FEMALE");

    private final String gender;

}