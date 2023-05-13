package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDto {

    private Long accountId;
    private String email;
    private String userName;
    private String role;
    private String accessToken;

}
