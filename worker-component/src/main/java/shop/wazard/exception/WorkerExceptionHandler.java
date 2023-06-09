package shop.wazard.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.wazard.util.exception.ErrorMessage;
import shop.wazard.util.exception.StatusEnum;

@RestControllerAdvice
public class WorkerExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> accountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(StatusEnum.ACCOUNT_NOT_FOUND.getStatusCode())
                                .errorMessage(e.getMessage())
                                .build());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorMessage> notAuthorizedException(NotAuthorizedException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(StatusEnum.NOT_AUTHORIZED.getStatusCode())
                                .errorMessage(e.getMessage())
                                .build());
    }

    @ExceptionHandler(JoinWorkerDeniedException.class)
    public ResponseEntity<ErrorMessage> joinWorkerDeniedException(JoinWorkerDeniedException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(StatusEnum.JOIN_WORKER_DENIED.getStatusCode())
                                .errorMessage(e.getMessage())
                                .build());
    }

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<ErrorMessage> contractNotFoundException(ContractNotFoundException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(StatusEnum.CONTRACT_NOT_FOUND.getStatusCode())
                                .errorMessage(e.getMessage())
                                .build());
    }

    @ExceptionHandler(WaitingListNotFoundException.class)
    public ResponseEntity<ErrorMessage> waitingListNotFoundException(
            WaitingListNotFoundException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(StatusEnum.WAITING_LIST_NOT_FOUND.getStatusCode())
                                .errorMessage(e.getMessage())
                                .build());
    }
}
