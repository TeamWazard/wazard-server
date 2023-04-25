package shop.wazard.util.response;

public enum StatusEnum {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    EXPIRED_TOKEN(401, "EXPIRED_TOKEN"),
    FAIL_TO_SEND_MAIL(600, "SMTP_SERVER_ERROR"),
    FAIL_TO_CREATE_MAIL_FROM(601, "FAIL_TO_CREATE_EMAIL_FORM");

    private int statusCode;
    private String message;

    StatusEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
