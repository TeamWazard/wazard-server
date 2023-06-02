package shop.wazard.application.port.in;

import java.util.List;
import shop.wazard.dto.*;

public interface MyPageService {

    List<GetPastWorkplaceResDto> getPastWorkplaces(GetPastWorkplaceReqDto getPastWorkplaceReqDto);

    GetMyPastWorkRecordResDto getMyPastWorkingRecord(
            GetMyPastWorkRecordReqDto getMyPastWorkingRecordReqDto);

    GetMyAttitudeScoreResDto getMyAttitudeScore(GetMyAttitudeScoreReqDto getMyAttitudeScoreReqDto);
}
