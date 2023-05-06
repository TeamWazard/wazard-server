package shop.wazard.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCompanyInfoReqDto {

    @NotBlank(message = "업장 id는 필수 입력 값입니다.")
    private Long companyId;

    @NotBlank(message = "로고 이미지 주소는 필수 입력 값입니다.")
    private String logoImageUrl;

    @NotBlank(message = "업장 명은 필수 입력 값입니다.")
    private String companyName;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String companyAddress;

    @NotBlank(message = "연락처는 필수 입력 값입니다.")
    private String companyContact;

    @NotBlank(message = "급여일은 필수 입력 값입니다.")
    private int salaryDate;

}
