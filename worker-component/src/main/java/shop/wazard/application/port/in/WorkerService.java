package shop.wazard.application.port.in;

import shop.wazard.dto.RegisterReplaceReqDto;
import shop.wazard.dto.RegisterReplaceResDto;
import shop.wazard.dto.GetMyReplaceRecordReqDto;
import shop.wazard.dto.GetMyReplaceRecordResDto;

import java.util.List;

public interface WorkerService {

    RegisterReplaceResDto registerReplace(RegisterReplaceReqDto registerReplaceReqDto);

    List<GetMyReplaceRecordResDto> getMyReplaceRecord(GetMyReplaceRecordReqDto getMyReplaceRecordReqDto);

}
