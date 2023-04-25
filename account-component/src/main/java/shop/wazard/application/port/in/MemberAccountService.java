package shop.wazard.application.port.in;

import shop.wazard.dto.JoinReqDto;
import shop.wazard.dto.JoinResDto;

public interface MemberAccountService {
    JoinResDto join(JoinReqDto joinReqDto);
}
