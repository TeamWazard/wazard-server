package shop.wazard.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.wazard.util.response.ErrorMessage;
import shop.wazard.util.response.StatusEnum;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class AccountExceptionHandler {

    // TODO : RuntimeException을 던지는 메서드들은 추후 구체적인 예외로 변경 필요
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> customMethod(Exception e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .statusEnum(StatusEnum.BAD_REQUEST)
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorMessage> jwtExceptionHandle(Exception e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .statusEnum(StatusEnum.BAD_REQUEST)
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .statusEnum(StatusEnum.BAD_REQUEST)
                        .errorMessage(e.getFieldError().getDefaultMessage())
                        .build()
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorMessage> handleBindException(BindException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .statusEnum(StatusEnum.BAD_REQUEST)
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    //TODO : 에러를 제대로 잡는지 검증 필요
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> handleJwtExpiredException(Exception e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .statusEnum(StatusEnum.BAD_REQUEST)
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException e) {
        return ResponseEntity.badRequest().body(
                ErrorMessage.builder()
                        .statusEnum(StatusEnum.BAD_REQUEST)
                        .errorMessage(e.getMessage())
                        .build()
        );
    }

}
