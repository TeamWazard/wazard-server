package shop.wazard.util.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    // 2XX Code
    OK("200", "요청 성공했습니다."),

    // 4XX Code
    BAD_REQUEST("400", "잘못된 요청입니다."),
    NOT_FOUND("404", "데이터를 찾을 수 없습니다."),
    NOT_FOUND_COMPANY_ACCOUNT("404_001_COM", "없는 고용주 계정입니다."),
    NOT_FOUND_MEMBER_ACCOUNT("404_001_MEM", "없는 알바생 계정입니다."),
    EXPIRED_TOKEN("401", "토큰이 만료되었습니다."),

    // 5XX Code
    INTERNAL_SERVER_ERROR("500", "내부 서버 문제가 발생했습니다."),

    // 6XX Code
    FAIL_TO_SEND_MAIL("600", "이메일 서버 문제가 발생했습니다."),
    FAIL_TO_CREATE_MAIL_FROM("601", "이메일 폼을 생성하지 못하였습니다.");

    private String statusCode;
    private String message;

    StatusEnum(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
