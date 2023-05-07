package shop.wazard.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCompanyInfoReqDto {

    @Positive
    private Long companyId;

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
