package shop.wazard.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.wazard.util.exception.ErrorMessage;
import shop.wazard.util.exception.StatusEnum;

@RestControllerAdvice
public class WorkerManagementExceptionHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCompanyNotFoundException(CompanyNotFoundException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.COMPANY_NOT_FOUND.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> accountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.ACCOUNT_NOT_FOUND.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(WorkerNotFoundInWaitingListException.class)
    public ResponseEntity<ErrorMessage> workerNotFoundInWaitingListException(WorkerNotFoundInWaitingListException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.ACCOUNT_NOT_FOUND.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

}
