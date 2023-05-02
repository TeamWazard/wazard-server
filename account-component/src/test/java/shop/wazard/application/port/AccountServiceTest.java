package shop.wazard.application.port;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
import shop.wazard.dto.CheckPasswordReqDto;
import shop.wazard.dto.CheckPasswordResDto;
import shop.wazard.util.jwt.JwtProvider;

import java.time.LocalDate;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AccountServiceImpl.class})
class AccountServiceTest {

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

    @Test
    @DisplayName("공통 - 비밀번호 확인 - 성공")
    public void checkPasswordSuccess() throws Exception {
        // given
        CheckPasswordReqDto checkPasswordReqDto = CheckPasswordReqDto.builder()
                .email("test@email.com")
                .password("Test@1234")
                .build();
        CheckPasswordResDto checkPasswordResDto = CheckPasswordResDto.builder()
                .message("인증되었습니다.")
                .build();
        GrantedAuthority[] grantedAuthority = {new SimpleGrantedAuthority("TEMP_ROLE")};
        User user = new User("test@email.com", "ENCRYPTED_PWD", Arrays.asList(grantedAuthority));

        // when
        Mockito.when(userDetailsService.loadUserByUsername(checkPasswordReqDto.getEmail()))
                .thenReturn(user);
        Mockito.when(passwordEncoder.matches(checkPasswordReqDto.getPassword(), user.getPassword()))
                .thenReturn(true);

        // then
        Assertions.assertDoesNotThrow(() -> accountService.checkPassword(checkPasswordReqDto));
    }

    @Test
    @DisplayName("공통 - 비밀번호 확인 - 비밀번호 불일치 - 실패")
    public void checkPasswordFailed_wrongPassword() throws Exception {
        // given
        CheckPasswordReqDto checkPasswordReqDto = CheckPasswordReqDto.builder()
                .email("test@email.com")
                .password("Test@1234")
                .build();
        GrantedAuthority[] grantedAuthority = {new SimpleGrantedAuthority("TEMP_ROLE")};
        User user = new User("test@email.com", "ENCRYPTED_PWD", Arrays.asList(grantedAuthority));

        // when
        Mockito.when(userDetailsService.loadUserByUsername(checkPasswordReqDto.getEmail()))
                .thenReturn(user);
        Mockito.when(passwordEncoder.matches(checkPasswordReqDto.getPassword(), user.getPassword()))
                .thenReturn(false);

        // then
        Assertions.assertThrows(BadCredentialsException.class, () -> accountService.checkPassword(checkPasswordReqDto));
    }

}