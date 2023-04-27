package shop.wazard.util.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorMessage {

    private int errorCode;
    private String errorMessage;

}
