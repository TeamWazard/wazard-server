package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.domain.AccountDomain;
import shop.wazard.dto.JoinReqDto;

import java.util.Optional;

@Component
class AccountDomainConverter {

    public Account joinReqDtoToAccount(JoinReqDto joinReqDto) {
        return Account.builder()
                .email(joinReqDto.getEmail())
                .password(joinReqDto.getPassword())
                .userName(joinReqDto.getUserName())
                .roles(joinReqDto.getRole())
                .build();
    }

    public Optional<AccountDomain> accountOptToAccountDomainOpt(Account account) {
        return Optional.of(
                AccountDomain.builder()
                        .id(account.getId())
                        .email(account.getEmail())
                        .password(account.getPassword())
                        .roles(account.getRoles())
                        .build()
        );
    }

}
