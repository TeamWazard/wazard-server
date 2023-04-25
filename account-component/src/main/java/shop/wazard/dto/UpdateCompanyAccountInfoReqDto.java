package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UpdateCompanyAccountInfoReqDto {

    private String email;
    private String userName;
    private String gender;
    private LocalDate birth;

}
