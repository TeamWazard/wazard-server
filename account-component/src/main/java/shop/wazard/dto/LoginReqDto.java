package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginReqDto {

    private String email;
    private String password;

}
