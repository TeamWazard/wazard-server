package shop.wazard.entity.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyAccountRelation {

    COMPANY("COMPANY"), MEMBER("MEMBER");

    public final String relation;
    
}
