package shop.wazard.application.port.out;

import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.dto.GetMyReplaceReqDto;
import shop.wazard.dto.GetMyReplaceResDto;

import java.util.List;

public interface WorkerPort {

    void saveReplace(String email, ReplaceInfo replaceInfo);

    List<GetMyReplaceResDto> getMyReplace(GetMyReplaceReqDto getMyReplaceReqDto);
}
