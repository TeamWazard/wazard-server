package shop.wazard.entity.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelationTypeJpa {

    EMPLOYER("EMPLOYER"), EMPLOYEE("EMPLOYEE");

    public final String relation;

}
