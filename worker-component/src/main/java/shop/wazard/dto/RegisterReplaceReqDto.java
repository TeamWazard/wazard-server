package shop.wazard.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterReplaceReqDto {

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Positive private Long companyId;

    private String replaceWorkerName;

    private LocalDate replaceDate;

    private LocalDateTime enterTime;

    private LocalDateTime exitTime;
}
