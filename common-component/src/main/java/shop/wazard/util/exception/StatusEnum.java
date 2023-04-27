package shop.wazard.util.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    FAIL_TO_SEND_MAIL(501, "메일 전송에 실패하였습니다."),
    FAIL_TO_CREATE_MAIL_FROM(502, "메일 폼 작성에 실패하였습니다."),
    ACCESS_DENIED(405, "본인 인증에 실패하였습니다."),
    IS_NOT_EXIST_ACCOUNT(406, "존재하지 않는 계정입니다."),
    IS_EXIST_ACCOUNT(407, "이미 가입된 정보가 있습니다.");

    private int statusCode;
    private String message;

    StatusEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
