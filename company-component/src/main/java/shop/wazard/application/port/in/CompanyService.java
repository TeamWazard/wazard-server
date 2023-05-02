package shop.wazard.application.port.in;

import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.RegisterCompanyResDto;

public interface CompanyService {

    RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto);

}
