package shop.wazard.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelationType {

    EMPLOYER("EMPLOYER"), EMPLOYEE("EMPLOYEE");

    public final String type;

}
