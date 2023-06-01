package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AbsentRecordDto {

    private LocalDate absentDate;

}
