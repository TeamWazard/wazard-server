package shop.wazard.dto;

import java.time.LocalDate;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterContractInfoReqDto {

    @Positive
    @NotBlank(message = "초대 알바생 id는 필수 입력 값입니다.")
    private Long accountId;

    @Positive
    @NotBlank(message = "업장 id는 필수 입력 값입니다.")
    private Long companyId;

    @NotBlank(message = "초대 코드는 필수 입력 값입니다.")
    private String invitationCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "계약 시작 날짜는 필수 입력 값입니다.")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "계약 종료 날짜는 필수 입력 값입니다.")
    private LocalDate endDate;

    @NotBlank(message = "업장 주소는 필수 입력 값입니다.")
    private String address;

    @NotBlank(message = "근로 시간은 필수 입력 값입니다.")
    private String workingTime;

    @Positive
    @NotBlank(message = "시급은 필수 입력 값입니다.")
    private int wage;

    @AssertFalse(message = "초기 계약 정보에 대한 동의 여부는 false입니다.")
    private boolean contractInfoAgreement;
}
