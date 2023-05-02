package shop.wazard.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.wazard.util.exception.ErrorMessage;
import shop.wazard.util.exception.StatusEnum;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class AccountExceptionHandler {

    // TODO : RuntimeException을 던지는 메서드들은 추후 구체적인 예외로 변경 필요
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorMessage> customMethod(Exception e) {
//        return ResponseEntity.badRequest().body(
//                ErrorMessage.builder()
//                        .errorCode(StatusEnum.BAD_REQUEST.getStatusCode())
//                        .errorMessage(e.getMessage())
//                        .build()
//        );
//    }

    // TODO : JWT 에러 핸들러 -> 수정 필요
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorMessage> jwtExceptionHandle(Exception e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.BAD_REQUEST.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }


    // TODO : 어떤 에러를 처리하는지?
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.BAD_REQUEST.getStatusCode())
                        .errorMessage(e.getFieldError().getDefaultMessage())
                        .build()
        );
    }

    // TODO : 어떤 에러를 처리하는지?
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorMessage> handleBindException(BindException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.BAD_REQUEST.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    //TODO : 에러를 제대로 잡는지 검증 필요
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> handleJwtExpiredException(Exception e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.EXPIRED_TOKEN.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }


    // 본인인증 실패(AOP)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.ACCESS_DENIED.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    // 존재하지 않는 회원
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> accountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.ACCOUNT_NOT_FOUND.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    // 회원가입 - 이미 가입된 메일 존재
    @ExceptionHandler(NestedEmailException.class)
    public ResponseEntity<ErrorMessage> nestedEmailException(NestedEmailException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.NESTED_EMAIL.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    // 비밀번호 불일치
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .errorCode(StatusEnum.ACCESS_DENIED.getStatusCode())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

}
