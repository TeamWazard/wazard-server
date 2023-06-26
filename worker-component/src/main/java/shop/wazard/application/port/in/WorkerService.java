package shop.wazard.application.port.in;

import java.util.List;
import shop.wazard.dto.*;

public interface WorkerService {

    RegisterReplaceResDto registerReplace(RegisterReplaceReqDto registerReplaceReqDto);

    List<GetMyReplaceRecordResDto> getMyReplaceRecord(
            GetMyReplaceRecordReqDto getMyReplaceRecordReqDto, Long companyId);

    PatchContractAgreementResDto modifyContractAgreement(
            PatchContractAgreementReqDto patchContractAgreementReqDto);
}
