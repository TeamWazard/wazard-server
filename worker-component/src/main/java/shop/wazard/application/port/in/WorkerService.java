package shop.wazard.application.port.in;

import shop.wazard.dto.GetMyReplaceReqDto;
import shop.wazard.dto.GetMyReplaceResDto;
import shop.wazard.dto.RegisterReplaceReqDto;
import shop.wazard.dto.RegisterReplaceResDto;

import java.util.List;

public interface WorkerService {

    RegisterReplaceResDto registerReplace(RegisterReplaceReqDto registerReplaceReqDto);

    List<GetMyReplaceResDto> getMyReplace(GetMyReplaceReqDto getMyReplaceReqDto);
}
