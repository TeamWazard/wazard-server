package shop.wazard.application.port;

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
import shop.wazard.application.port.out.LoadAccountPort;
import shop.wazard.application.port.out.SaveAccountPort;
import shop.wazard.application.port.out.UpdateAccountPort;
import shop.wazard.dto.*;
import shop.wazard.exception.AccountNotFoundException;
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
    private final SaveAccountPort saveAccountPort;
    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountPort updateAccountPort;

    @Override
    @Transactional
    public JoinResDto join(JoinReqDto joinReqDto) {
        loadAccountPort.doubleCheckEmail(joinReqDto.getEmail());
        Account account = Account.createAccount(joinReqDto);
        account.getMyProfile().setEncodedPassword(passwordEncoder.encode(joinReqDto.getPassword()));
        saveAccountPort.save(account);
        return JoinResDto.builder()
                .message("회원가입에 성공하였습니다.")
                .build();
    }

    @Override
    public LoginResDto login(LoginReqDto loginReqDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginReqDto.getEmail());
        if (!passwordEncoder.matches(loginReqDto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        Long accountId = loadAccountPort.findAccountIdByEmail(loginReqDto.getEmail());
        log.info("========== 회원가입을 시도한 회원의 email = {}, accountId = {} ==========", loginReqDto.getEmail(), accountId);
        return LoginResDto.builder()
                .accountId(accountId)
                .accessToken(jwtProvider.createAccessToken(authentication, accountId))
                .build();
    }

    @Override
    @Transactional
    public UpdateMyProfileResDto updateMyProfile(UpdateMyProfileReqDto updateMyProfileReqDto) {
        Account account = loadAccountPort.findAccountByEmail(updateMyProfileReqDto.getEmail())
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        account.getMyProfile().updateMyProfile(updateMyProfileReqDto);
        updateAccountPort.updateMyProfile(account);
        return UpdateMyProfileResDto.builder()
                .message("수정 완료되었습니다.")
                .build();
    }

}
