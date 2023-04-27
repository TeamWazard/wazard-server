package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.dto.JoinReqDto;

@Component
class AccountDomainConverter {

    public Account joinReqDtoToAccount(JoinReqDto joinReqDto) {
        return Account.builder()
                .email(joinReqDto.getEmail())
                .password(joinReqDto.getPassword())
                .userName(joinReqDto.getUserName())
                .gender(joinReqDto.getGender())
                .birth(joinReqDto.getBirth())
                .roles(joinReqDto.getRole())
                .build();
    }

}
