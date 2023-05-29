package shop.wazard.application.port.in;

import shop.wazard.dto.RegisterReplaceReqDto;
import shop.wazard.dto.RegisterReplaceResDto;

public interface WorkerService {

    RegisterReplaceResDto registerReplace(RegisterReplaceReqDto registerReplaceReqDto);
}
