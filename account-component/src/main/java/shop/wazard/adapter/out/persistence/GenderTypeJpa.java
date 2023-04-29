package shop.wazard.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum GenderTypeJpa {

    MALE("MALE"), FEMALE("FEMALE");

    private final String gender;

}