package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDto {

    // TODO : 회원 pk를 함께 넘겨줘야 함
    private String accessToken;

}
