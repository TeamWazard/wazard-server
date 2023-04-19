package shop.wazard.application.port.out;

import shop.wazard.dto.JoinReqDto;

public interface SaveAccountPort {
    void save(JoinReqDto joinReqDto);

}
