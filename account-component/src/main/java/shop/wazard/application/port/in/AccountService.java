package shop.wazard.application.port.in;

import shop.wazard.dto.*;

public interface AccountService {

    JoinResDto join(JoinReqDto joinReqDto);

    LoginResDto login(LoginReqDto loginReqDto);

    UpdateMyProfileResDto updateMyProfile(UpdateMyProfileReqDto updateMyProfileReqDto);

}
