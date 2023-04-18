package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class JoinReqDto {

    private String email;
    private String password;

    private String userName;

    private String gender;

    private LocalDate birth;

    private String role;

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

}
