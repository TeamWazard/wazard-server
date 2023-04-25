package shop.wazard.application.port.in;

import shop.wazard.dto.LoginReqDto;
import shop.wazard.dto.LoginResDto;

public interface CommonAccountService {
    LoginResDto login(LoginReqDto loginReqDto);
}
