package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetMyPastWorkRecordResDto {

    private String companyName;
    private String companyAddress;
    private String companyContact;
    private String companyLogoImage;
    private int tardyCount;
    private int absentCount;
    private double workScore;
    private LocalDate startWorkingDate;
    private LocalDate endWorkingDate;

}
