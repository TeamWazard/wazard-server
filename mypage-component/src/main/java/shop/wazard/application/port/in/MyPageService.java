package shop.wazard.application.port.in;

import shop.wazard.dto.GetMyPastWorkRecordReqDto;
import shop.wazard.dto.GetMyPastWorkRecordResDto;
import shop.wazard.dto.GetPastWorkplaceReqDto;
import shop.wazard.dto.GetPastWorkplaceResDto;

import java.util.List;

public interface MyPageService {

    List<GetPastWorkplaceResDto> getPastWorkplaces(GetPastWorkplaceReqDto getPastWorkplaceReqDto);

    GetMyPastWorkRecordResDto getMyPastWorkRecord(GetMyPastWorkRecordReqDto getMyPastWorkRecordReqDto);

}
