package shop.wazard.application.port.domain;

import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.JoinReqDto;
import shop.wazard.entity.common.BaseEntity.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
public class Account {

    private Long id;
    private MyProfile myProfile;

    private State state;
    private String roles;

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    public static Account createAccount(JoinReqDto joinReqDto) {
        return Account.builder()
                .myProfile(MyProfile.builder()
                        .email(joinReqDto.getEmail())
                        .password(joinReqDto.getPassword())
                        .userName(joinReqDto.getUserName())
                        .gender(GenderType.valueOf(joinReqDto.getGender()))
                        .birth(joinReqDto.getBirth())
                        .phoneNumber(joinReqDto.getPhoneNumber())
                        .build())
                .roles(joinReqDto.getRole())
                .state(State.ACTIVE)
                .build();
    }

}
