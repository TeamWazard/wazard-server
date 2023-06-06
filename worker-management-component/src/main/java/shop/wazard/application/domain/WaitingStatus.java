package shop.wazard.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WaitingStatus {
    INVITED("INVITED"),
    AGREED("AGREED"),
    DISAGREED("DISAGREED"),
    JOINED("JOINED"),
    ;

    public final String status;
}
