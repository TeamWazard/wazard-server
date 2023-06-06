package shop.wazard.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AbsentRecordDto {

    private LocalDate absentDate;
}
