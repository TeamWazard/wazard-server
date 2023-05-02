package shop.wazard.application.port;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.GenderType;
import shop.wazard.application.port.domain.MyProfile;
import shop.wazard.application.port.in.AccountService;
import shop.wazard.application.port.out.LoadAccountPort;
import shop.wazard.application.port.out.SaveAccountPort;
import shop.wazard.application.port.out.UpdateAccountPort;
import shop.wazard.dto.UpdateMyProfileReqDto;
import shop.wazard.util.jwt.JwtProvider;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AccountServiceImpl.class})
class AccountServiceTestJpa {

    @Autowired
    private AccountService accountService;

    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private SaveAccountPort saveAccountPort;
    @MockBean
    private LoadAccountPort loadAccountPort;
    @MockBean
    private UpdateAccountPort updateAccountPort;

    @Test
    @DisplayName("고용주 - 회원정보 수정 성공")
    public void updateCompanyAccountInfoSuccess() throws Exception {
        // given
        UpdateMyProfileReqDto updateMyProfileReqDto = UpdateMyProfileReqDto.builder()
                .email("test@email.com")
                .userName("갑")
                .phoneNumber("010-1111-1111")
                .gender(GenderType.MALE)
                .birth(LocalDate.of(2000, 1, 1))
                .build();
        MyProfile myProfile = MyProfile.builder()
                .email("test1@email.com")
                .userName("을")
                .phoneNumber("010-9999-9999")
                .gender(GenderType.FEMALE)
                .birth(LocalDate.of(2000, 1, 1))
                .build();
        Account account = Account.builder().myProfile(myProfile).build();

        // when
        Mockito.when(loadAccountPort.findAccountByEmail(updateMyProfileReqDto.getEmail())).thenReturn(account);
        account.getMyProfile().updateMyProfile(updateMyProfileReqDto);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(account.getMyProfile().getEmail(), updateMyProfileReqDto.getEmail()),
                () -> Assertions.assertEquals(account.getMyProfile().getUserName(), updateMyProfileReqDto.getUserName()),
                () -> Assertions.assertEquals(account.getMyProfile().getPhoneNumber(), updateMyProfileReqDto.getPhoneNumber()),
                () -> Assertions.assertEquals(account.getMyProfile().getGender(), updateMyProfileReqDto.getGender()),
                () -> Assertions.assertEquals(account.getMyProfile().getBirth(), updateMyProfileReqDto.getBirth())
        );
    }

}
