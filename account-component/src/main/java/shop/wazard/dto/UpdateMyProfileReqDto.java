package shop.wazard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.*;
import shop.wazard.application.domain.GenderType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMyProfileReqDto {

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;

    @NotNull(message = "성별은 필수 입력 값입니다.")
    private GenderType gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$")
    @NotBlank
    private String phoneNumber;
}
