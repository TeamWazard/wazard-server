package shop.wazard.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetWorkerAttendanceRecordResDto {

    private String userName;
    private List<CommuteRecordDto> commuteRecordResDtoList;
    private List<AbsentRecordDto> absentRecordResDtoList;
}
