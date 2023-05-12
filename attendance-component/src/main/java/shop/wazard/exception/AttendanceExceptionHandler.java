package shop.wazard.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.wazard.util.exception.ErrorMessage;
import shop.wazard.util.exception.StatusEnum;

@ControllerAdvice
public class AttendanceExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> accountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.ACCOUNT_NOT_FOUND.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorMessage> notAuthorizedException(NotAuthorizedException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.NOT_AUTHORIZED.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

}
