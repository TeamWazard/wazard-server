package shop.wazard.application.port.out;

import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.dto.GetMyReplaceRecordReqDto;
import shop.wazard.dto.GetMyReplaceRecordResDto;

import java.util.List;

public interface WorkerPort {

    void saveReplace(String email, ReplaceInfo replaceInfo);

    List<GetMyReplaceRecordResDto> getMyReplaceRecord(GetMyReplaceRecordReqDto getMyReplaceRecordReqDto);

}
