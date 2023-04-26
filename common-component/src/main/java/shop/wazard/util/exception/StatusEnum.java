package shop.wazard.util.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    EXPIRED_TOKEN(401),
    FAIL_TO_SEND_MAIL(500),
    FAIL_TO_CREATE_MAIL_FROM(500),
    ACCESS_DENIED(415);

    private int statusCode;

    StatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

}
