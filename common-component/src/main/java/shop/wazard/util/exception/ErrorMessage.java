package shop.wazard.util.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorMessage {

    private StatusEnum statusEnum;
    private Object errorMessage;

}
