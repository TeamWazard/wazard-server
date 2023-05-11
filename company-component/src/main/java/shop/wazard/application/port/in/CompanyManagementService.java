package shop.wazard.application.port.in;

import shop.wazard.dto.*;

import java.util.List;

public interface CompanyManagementService {

    RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto);

    UpdateCompanyInfoResDto updateCompanyInfo(UpdateCompanyInfoReqDto updateCompanyInfoReqDto);

    DeleteCompanyResDto deleteCompany(DeleteCompanyReqDto deleteCompanyReqDto);

    List<GetOwnedCompanyResDto> getOwnedCompanyList(Long accountId, GetOwnedCompanyReqDto getOwnedCompanyReqDto);

}
