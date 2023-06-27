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
    ACCOUNT_NOT_FOUND(406, "존재하지 않는 계정입니다."),
    NESTED_EMAIL(407, "이미 가입된 정보가 있습니다."),
    NOT_AUTHORIZED(408, "권한이 없습니다."),
    COMPANY_NOT_FOUND(409, "존재하지 않는 업장입니다."),
    JOIN_WORKER_DENIED(410, "계약 정보에 동의하지 않은 근무자는 초대 수락이 불가능합니다."),
    WORKER_NOT_FOUND_IN_WAITING_LIST(411, "대기자 명단에 존재하지 않는 근무자입니다."),
    INVALID_TARDY_STATE(412, "퇴근 등록에는 지각처리가 불가합니다."),
    FAIL_TO_UPLOAD_IMAGE(503, "이미지 업로드에 실패했습니다."),
    ENTER_RECORD_NOT_FOUND(413, "출근 기록이 존재하지 않습니다."),
    ROSTER_NOT_FOUND(414, "업장과 근로자의 관계를 찾을 수 없습니다."),
    UNSUPPORTED_DATE_RANGE(415, "지원되지 않는 날짜입니다."),
    CONTRACT_NOT_FOUND(416, "존재하지 않는 계약정보입니다."),
    WAITING_LIST_NOT_FOUND(417, "존재하지 않는 대기자입니다."),
    INVITECODE_NOT_FOUND(418,"존재하지 않는 초대코드입니다."),
    ;

    private int statusCode;
    private String message;

    StatusEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
