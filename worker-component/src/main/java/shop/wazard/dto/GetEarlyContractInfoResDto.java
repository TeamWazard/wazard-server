package shop.wazard.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetEarlyContractInfoResDto {

    private String userName;

    private String companyName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String address;

    private String workingTime;

    private int wage;

}
