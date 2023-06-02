package shop.wazard.application.port.in;

import java.util.List;
import shop.wazard.dto.*;

public interface CompanyService {

    RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto);

    UpdateCompanyInfoResDto updateCompanyInfo(UpdateCompanyInfoReqDto updateCompanyInfoReqDto);

    DeleteCompanyResDto deleteCompany(DeleteCompanyReqDto deleteCompanyReqDto);

    List<GetOwnedCompanyResDto> getOwnedCompanyList(
            Long accountId, GetOwnedCompanyReqDto getOwnedCompanyReqDto);

    List<GetBelongedCompanyResDto> getBelongedCompanyList(
            Long accountId, GetBelongedCompanyReqDto getBelongedCompanyReqDto);
}
