package shop.wazard.application.port.in;

import shop.wazard.dto.JoinReqDto;
import shop.wazard.dto.JoinResDto;
import shop.wazard.dto.UpdateCompanyAccountInfoReqDto;
import shop.wazard.dto.UpdateCompanyAccountInfoResDto;

public interface CompanyAccountService {
    JoinResDto join(JoinReqDto joinReqDto);

    UpdateCompanyAccountInfoResDto updateCompanyAccountInfo(UpdateCompanyAccountInfoReqDto updateCompanyAccountInfoReqDto);
}
