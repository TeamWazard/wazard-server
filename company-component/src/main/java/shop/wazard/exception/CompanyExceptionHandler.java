package shop.wazard.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.wazard.util.exception.ErrorMessage;
import shop.wazard.util.exception.StatusEnum;

@RestControllerAdvice
public class CompanyExceptionHandler {

    // 고용주가 아닌 회원이 접근
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorMessage> handleNotAuthorizedException(NotAuthorizedException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.NOT_AUTHORIZED.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    // 존재하지 않는 업장
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCompanyNotFoundException(CompanyNotFoundException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.COMPANY_NOT_FOUND.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

}
