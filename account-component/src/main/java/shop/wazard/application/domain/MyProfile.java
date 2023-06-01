package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.UpdateMyProfileReqDto;

import java.time.LocalDate;

@Getter
@Builder
public class MyProfile {

    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private GenderType gender;
    private LocalDate birth;

    public void updateMyProfile(UpdateMyProfileReqDto updateMyProfileReqDto) {
        this.email = updateMyProfileReqDto.getEmail();
        this.userName = updateMyProfileReqDto.getUserName();
        this.phoneNumber = updateMyProfileReqDto.getPhoneNumber();
        this.gender = GenderType.valueOf(updateMyProfileReqDto.getGender().toString());
        this.birth = updateMyProfileReqDto.getBirth();
    }

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

}
