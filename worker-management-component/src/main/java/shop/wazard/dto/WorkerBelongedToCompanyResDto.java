package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.application.domain.GenderType;

import java.time.LocalDate;

@Getter
@Builder
public class WorkerBelongedToCompanyResDto {

    private Long accountId;
    private String userName;
    private LocalDate birth;
    private GenderType genderType;
    private String address;

}
