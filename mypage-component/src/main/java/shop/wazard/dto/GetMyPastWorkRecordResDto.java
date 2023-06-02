package shop.wazard.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

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
