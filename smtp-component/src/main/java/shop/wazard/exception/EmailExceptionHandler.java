package shop.wazard.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.wazard.util.response.ErrorMessage;
import shop.wazard.util.response.StatusEnum;

@ControllerAdvice
public class EmailExceptionHandler {

    @ExceptionHandler(FailSendEmail.class)
    public ResponseEntity<ErrorMessage> failSendEmail(Exception e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .statusEnum(StatusEnum.FAIL_TO_SEND_MAIL)
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(FailCreateEmailForm.class)
    public ResponseEntity<ErrorMessage> failCreateEmailForm(Exception e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .statusEnum(StatusEnum.FAIL_TO_CREATE_MAIL_FROM)
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

}
