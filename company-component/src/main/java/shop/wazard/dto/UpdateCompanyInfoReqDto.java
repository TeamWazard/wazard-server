package shop.wazard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCompanyInfoReqDto {

    @Positive private Long companyId;

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "로고 이미지 주소는 필수 입력 값입니다.")
    private String logoImageUrl;

    @NotBlank(message = "업장 명은 필수 입력 값입니다.")
    private String companyName;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String companyAddress;

    @NotBlank(message = "연락처는 필수 입력 값입니다.")
    private String companyContact;

    @Range(min = 0, max = 31)
    private int salaryDate;
}
