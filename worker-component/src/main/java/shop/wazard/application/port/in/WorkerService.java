package shop.wazard.application.port.in;

import java.util.List;
import shop.wazard.dto.GetMyReplaceRecordReqDto;
import shop.wazard.dto.GetMyReplaceRecordResDto;
import shop.wazard.dto.RegisterReplaceReqDto;
import shop.wazard.dto.RegisterReplaceResDto;

public interface WorkerService {

    RegisterReplaceResDto registerReplace(RegisterReplaceReqDto registerReplaceReqDto);

    List<GetMyReplaceRecordResDto> getMyReplaceRecord(
            GetMyReplaceRecordReqDto getMyReplaceRecordReqDto, Long companyId);
}
