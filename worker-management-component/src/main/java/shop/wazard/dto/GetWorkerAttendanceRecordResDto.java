package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetWorkerAttendanceRecordResDto {

    private String userName;
    private List<CommuteRecordDto> commuteRecordResDtoList;
    private List<AbsentRecordDto> absentRecordResDtoList;

}
