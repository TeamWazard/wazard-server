package shop.wazard.util.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDto {
    private String statusCode;
    private String message;
}
