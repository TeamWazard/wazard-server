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
import shop.wazard.application.port.in.CommonAccountService;
import shop.wazard.application.port.out.LoadAccountPort;
import shop.wazard.dto.LoginReqDto;
import shop.wazard.dto.LoginResDto;
import shop.wazard.util.jwt.JwtProvider;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonAccountServiceImpl implements CommonAccountService {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final LoadAccountPort loadAccountPort;


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
                .accessToken(jwtProvider.createAccessToken(authentication, accountId))
                .build();
    }
}
