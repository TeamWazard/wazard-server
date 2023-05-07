package shop.wazard.application.port.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelationType {

    EMPLOYER("EMPLOYER"), EMPLOYEE("EMPLOYEE");

    public final String relation;

}
