package shop.wazard.adapter.in.rest;

import org.springframework.stereotype.Component;
import shop.wazard.adapter.in.rest.request.JoinReq;
import shop.wazard.adapter.in.rest.request.LoginReq;
import shop.wazard.adapter.in.rest.response.JoinRes;
import shop.wazard.adapter.in.rest.response.LoginRes;
import shop.wazard.dto.JoinReqDto;
import shop.wazard.dto.JoinResDto;
import shop.wazard.dto.LoginReqDto;
import shop.wazard.dto.LoginResDto;

@Component
class ControllerConverter {

    public LoginReqDto loginReqToLoginReqDto(LoginReq loginReq) {
        return LoginReqDto.builder()
                .email(loginReq.getEmail())
                .password(loginReq.getPassword())
                .build();
    }

    public LoginRes loginResDtoToLoginRes(LoginResDto loginResDto) {
        return LoginRes.builder()
                .accessToken(loginResDto.getAccessToken())
                .build();
    }

    public JoinRes joinResDtoToJoinRes(JoinResDto joinResDto) {
        return JoinRes.builder()
                .message(joinResDto.getMessage())
                .build();
    }

    public JoinReqDto joinReqToJoinReqDto(JoinReq joinReq) {
        return JoinReqDto.builder()
                .email(joinReq.getEmail())
                .password(joinReq.getPassword())
                .userName(joinReq.getUserName())
                .gender(joinReq.getGender())
                .birth(joinReq.getBirth())
                .role(joinReq.getRole())
                .build();
    }

}
