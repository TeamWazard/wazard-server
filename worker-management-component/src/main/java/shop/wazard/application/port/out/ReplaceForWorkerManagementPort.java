package shop.wazard.application.port.out;

import shop.wazard.dto.GetAllReplaceReqDto;
import shop.wazard.dto.GetAllReplaceResDto;

import java.util.List;

public interface ReplaceForWorkerManagementPort {

    List<GetAllReplaceResDto> getAllReplace(GetAllReplaceReqDto getAllReplaceReqDto);

}
