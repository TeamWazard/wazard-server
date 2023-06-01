package shop.wazard.application.port.out;

import shop.wazard.dto.GetAllReplaceRecordReqDto;
import shop.wazard.dto.GetAllReplaceRecordResDto;

import java.util.List;

public interface ReplaceForWorkerManagementPort {

    List<GetAllReplaceRecordResDto> getAllReplaceRecord(GetAllReplaceRecordReqDto getAllReplaceRecordReqDto);

}
