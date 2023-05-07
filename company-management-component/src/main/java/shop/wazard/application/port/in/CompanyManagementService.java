package shop.wazard.application.port.in;

import shop.wazard.dto.*;

public interface CompanyManagementService {

    RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto);

    UpdateCompanyInfoResDto updateCompanyInfo(UpdateCompanyInfoReqDto updateCompanyInfoReqDto);

    DeleteCompanyResDto deleteCompany(DeleteCompanyReqDto deleteCompanyReqDto);

}
