package shop.wazard.application.port.in;

import shop.wazard.dto.JoinReqDto;
import shop.wazard.dto.JoinResDto;
import shop.wazard.dto.LoginReqDto;
import shop.wazard.dto.LoginResDto;

public interface CompanyAccountService {
    JoinResDto join(JoinReqDto joinReqDto);

    LoginResDto login(LoginReqDto loginReqDto);

}
