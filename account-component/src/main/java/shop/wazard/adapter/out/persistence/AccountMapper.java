package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.AccountStatus;
import shop.wazard.application.port.domain.GenderType;
import shop.wazard.application.port.domain.MyProfile;

import java.util.Optional;

@Component
class AccountMapper {

    public AccountJpa toAccountJpa(Account account) {
        return AccountJpa.builder()
                .email(account.getMyProfile().getEmail())
                .password(account.getMyProfile().getPassword())
                .userName(account.getMyProfile().getUserName())
                .phoneNumber(account.getMyProfile().getPhoneNumber())
                .gender(account.getMyProfile().getGender().toString())
                .birth(account.getMyProfile().getBirth())
                .roles(account.getRoles())
                .accountStatusJpa(AccountStatusJpa.valueOf(account.getAccountStatus().toString()))
                .build();
    }

    public Optional<Account> toAccountDomain(AccountJpa accountJpa) {
        return Optional.of(Account.builder()
                .id(accountJpa.getId())
                .myProfile(MyProfile.builder()
                        .email(accountJpa.getEmail())
                        .userName(accountJpa.getUserName())
                        .phoneNumber(accountJpa.getPhoneNumber())
                        .gender(GenderType.valueOf(accountJpa.getGender().toString()))
                        .birth(accountJpa.getBirth())
                        .build())
                .accountStatus(AccountStatus.valueOf(accountJpa.getAccountStatusJpa().toString()))
                .roles(accountJpa.getRoles())
                .build());
    }

    public Optional<Account> toAccountForSecurity(AccountJpa accountJpa) {
        return Optional.of(Account.builder()
                .myProfile(MyProfile.builder()
                        .email(accountJpa.getEmail())
                        .password(accountJpa.getPassword())
                        .build())
                .roles(accountJpa.getRoles())
                .build()
        );
    }

}
