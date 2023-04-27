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
import shop.wazard.adapter.out.persistence.Account;
import shop.wazard.adapter.out.persistence.GenderType;
import shop.wazard.application.port.in.CompanyAccountService;
import shop.wazard.application.port.out.LoadAccountPort;
import shop.wazard.application.port.out.SaveAccountPort;
import shop.wazard.dto.UpdateCompanyAccountInfoReqDto;
import shop.wazard.util.jwt.JwtProvider;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyAccountServiceImpl.class})
class CompanyAccountServiceTest {

    @Autowired
    private CompanyAccountService companyAccountService;
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

    @Test
    @DisplayName("성공 - 회원 정보 수정")
    public void updateCompanyAccountInfoSuccess() throws Exception {
        // given
        UpdateCompanyAccountInfoReqDto updateCompanyAccountInfoReqDto = UpdateCompanyAccountInfoReqDto.builder()
                .email("test@email.com")
                .userName("갑")
                .phoneNumber("010-1111-1111")
                .gender(GenderType.MALE)
                .birth(LocalDate.of(2000, 1, 1))
                .build();
        Account account = Account.builder()
                .email("test1@email.com")
                .userName("을")
                .phoneNumber("010-9999-9999")
                .gender(GenderType.FEMALE)
                .birth(LocalDate.of(2000, 1, 1))
                .build();

        // when
        Mockito.when(loadAccountPort.findAccountByEmail(updateCompanyAccountInfoReqDto.getEmail())).thenReturn(Optional.ofNullable(account));
        account.updateCompanyAccountInfo(updateCompanyAccountInfoReqDto);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(account.getEmail(), updateCompanyAccountInfoReqDto.getEmail()),
                () -> Assertions.assertEquals(account.getUserName(), updateCompanyAccountInfoReqDto.getUserName()),
                () -> Assertions.assertEquals(account.getPhoneNumber(), updateCompanyAccountInfoReqDto.getPhoneNumber()),
                () -> Assertions.assertEquals(account.getGender(), updateCompanyAccountInfoReqDto.getGender()),
                () -> Assertions.assertEquals(account.getBirth(), updateCompanyAccountInfoReqDto.getBirth())
        );
    }

    @Test
    @DisplayName("실패 - 존재하지 않는 회원 정보 조회 - 회원 정보 수정")
    public void updateCompanyAccountInfoFail() {
        // given
        UpdateCompanyAccountInfoReqDto updateCompanyAccountInfoReqDto = UpdateCompanyAccountInfoReqDto.builder()
                .email("test@email.com")
                .userName("갑")
                .phoneNumber("010-1111-1111")
                .gender(GenderType.MALE)
                .birth(LocalDate.of(2000, 1, 1))
                .build();

        //when
        Mockito.when(loadAccountPort.findAccountByEmail(updateCompanyAccountInfoReqDto.getEmail()))
                .thenThrow(new IsNotExistAccountException());

        //then
        Assertions.assertThrows(IsNotExistAccountException.class,
                () -> companyAccountService.updateCompanyAccountInfo(updateCompanyAccountInfoReqDto));

    }
}
