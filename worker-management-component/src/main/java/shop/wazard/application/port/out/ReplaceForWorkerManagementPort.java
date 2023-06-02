package shop.wazard.application.port.out;

import java.util.List;
import shop.wazard.dto.GetAllReplaceRecordReqDto;
import shop.wazard.dto.GetAllReplaceRecordResDto;

public interface ReplaceForWorkerManagementPort {

    List<GetAllReplaceRecordResDto> getAllReplaceRecord(
            GetAllReplaceRecordReqDto getAllReplaceRecordReqDto);
}
