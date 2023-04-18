package shop.wazard.adapter.in.rest.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRes {

    // TODO : 회원 pk를 함께 넘겨줘야 함
    private String accessToken;

}
