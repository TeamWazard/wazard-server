package shop.wazard.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.wazard.util.exception.ErrorMessage;
import shop.wazard.util.exception.StatusEnum;

@ControllerAdvice
public class EmailExceptionHandler {

    @ExceptionHandler(FailSendEmail.class)
    public ResponseEntity<ErrorMessage> failSendEmail(Exception e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(StatusEnum.FAIL_TO_SEND_MAIL.getStatusCode())
                                .errorMessage(StatusEnum.FAIL_TO_SEND_MAIL.getMessage())
                                .build());
    }

    @ExceptionHandler(FailCreateEmailForm.class)
    public ResponseEntity<ErrorMessage> failCreateEmailForm(Exception e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(StatusEnum.FAIL_TO_CREATE_MAIL_FROM.getStatusCode())
                                .errorMessage(StatusEnum.FAIL_TO_CREATE_MAIL_FROM.getMessage())
                                .build());
    }
}
