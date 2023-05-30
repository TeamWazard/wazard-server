package shop.wazard.application.port.in;

import shop.wazard.dto.*;

import java.util.List;

public interface MyPageService {

    List<GetPastWorkplaceResDto> getPastWorkplaces(GetPastWorkplaceReqDto getPastWorkplaceReqDto);

    GetMyPastWorkRecordResDto getMyPastWorkingRecord(GetMyPastWorkRecordReqDto getMyPastWorkingRecordReqDto);

    GetMyAttitudeScoreResDto getMyAttitudeScore(GetMyAttitudeScoreReqDto getMyAttitudeScoreReqDto);
    
}
