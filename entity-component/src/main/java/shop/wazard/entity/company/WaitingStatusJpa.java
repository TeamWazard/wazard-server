package shop.wazard.entity.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WaitingStatusJpa {

    // 초대만, 가입 가능, 가입 불가능, 가입된
    INVITED("INVITED"),
    AGREED("AGREED"),
    DISAGREED("DISAGREED"),
    JOINED("JOINED"),
    ;

    public final String status;
}
