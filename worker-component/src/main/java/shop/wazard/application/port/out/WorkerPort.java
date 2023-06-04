package shop.wazard.application.port.out;

import java.util.List;
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.dto.GetMyReplaceRecordReqDto;
import shop.wazard.dto.GetMyReplaceRecordResDto;

public interface WorkerPort {

    void saveReplace(String email, ReplaceInfo replaceInfo);

    List<GetMyReplaceRecordResDto> getMyReplaceRecord(
            GetMyReplaceRecordReqDto getMyReplaceRecordReqDto, Long companyId);
}
