package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AbsentForAttendance {

    private Long accountId;
    private Long companyId;

}
