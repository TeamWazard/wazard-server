package shop.wazard.application.port.in;

import shop.wazard.dto.*;

import java.util.List;

public interface WorkerService {

    RegisterReplaceResDto registerReplace(RegisterReplaceReqDto registerReplaceReqDto);

    List<GetMyReplaceRecordResDto> getMyReplaceRecord(
            GetMyReplaceRecordReqDto getMyReplaceRecordReqDto, Long companyId);

    GetEarlyContractInfoResDto getEarlyContractInfo(GetEarlyContractInfoReqDto getEarlyContractInfoReqDto);
}
