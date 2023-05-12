package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.in.AccountService;
import shop.wazard.application.port.out.AccountPort;
import shop.wazard.dto.*;
import shop.wazard.exception.NestedEmailException;
import shop.wazard.util.exception.StatusEnum;
import shop.wazard.util.jwt.JwtProvider;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final AccountPort accountPort;

    @Override
    @Transactional
    public JoinResDto join(JoinReqDto joinReqDto) {
        if (!accountPort.isPossibleEmail(joinReqDto.getEmail())) {
            throw new NestedEmailException(StatusEnum.NESTED_EMAIL.getMessage());
        }
        Account account = Account.createAccount(joinReqDto);
        account.getMyProfile().setEncodedPassword(passwordEncoder.encode(joinReqDto.getPassword()));
        accountPort.save(account);
        return JoinResDto.builder()
                .message("회원가입에 성공하였습니다.")
                .build();
    }

    @Override
    public LoginResDto login(LoginReqDto loginReqDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginReqDto.getEmail());
        if (!passwordEncoder.matches(loginReqDto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException(StatusEnum.ACCESS_DENIED.getMessage());
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        Account account = accountPort.findAccountByEmail(loginReqDto.getEmail());
        return LoginResDto.builder()
                .accountId(account.getId())
                .email(account.getMyProfile().getEmail())
                .userName(account.getMyProfile().getUserName())
                .role(account.getRoles())
                .accessToken(jwtProvider.createAccessToken(authentication, account.getId()))
                .build();
    }

    @Override
    @Transactional
    public UpdateMyProfileResDto updateMyProfile(UpdateMyProfileReqDto updateMyProfileReqDto) {
        Account account = accountPort.findAccountByEmail(updateMyProfileReqDto.getEmail());
        account.getMyProfile().updateMyProfile(updateMyProfileReqDto);
        accountPort.updateMyProfile(account);
        return UpdateMyProfileResDto.builder()
                .message("수정 완료되었습니다.")
                .build();
    }

    @Override
    public CheckPasswordResDto checkPassword(CheckPasswordReqDto checkPasswordReqDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(checkPasswordReqDto.getEmail());
        if (!passwordEncoder.matches(checkPasswordReqDto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException(StatusEnum.ACCESS_DENIED.getMessage());
        }
        return CheckPasswordResDto.builder()
                .message("인증되었습니다.")
                .build();
    }

}
