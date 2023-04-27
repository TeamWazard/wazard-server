package shop.wazard.application.port;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.adapter.out.persistence.Account;
import shop.wazard.application.port.in.CompanyAccountService;
import shop.wazard.application.port.out.LoadAccountPort;
import shop.wazard.application.port.out.SaveAccountPort;
import shop.wazard.dto.JoinReqDto;
import shop.wazard.dto.JoinResDto;
import shop.wazard.dto.UpdateCompanyAccountInfoReqDto;
import shop.wazard.dto.UpdateCompanyAccountInfoResDto;
import shop.wazard.util.exception.WazardException;

import static shop.wazard.util.response.StatusEnum.BAD_REQUEST;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
class CompanyAccountServiceImpl implements CompanyAccountService {

    private final PasswordEncoder passwordEncoder;
    private final SaveAccountPort saveAccountPort;
    private final LoadAccountPort loadAccountPort;

    @Override
    @Transactional
    public JoinResDto join(JoinReqDto joinReqDto) {
        loadAccountPort.doubleCheckEmail(joinReqDto.getEmail());
        joinReqDto.setEncodedPassword(passwordEncoder.encode(joinReqDto.getPassword()));
        saveAccountPort.save(joinReqDto);
        return JoinResDto.builder()
                .message("회원가입에 성공하였습니다.")
                .build();
    }

    @Override
    @Transactional
    public UpdateCompanyAccountInfoResDto updateCompanyAccountInfo(UpdateCompanyAccountInfoReqDto updateCompanyAccountInfoReqDto) {
        final Account account =
                loadAccountPort.findAccountByEmail(updateCompanyAccountInfoReqDto.getEmail())
                .orElseThrow(() -> new WazardException(BAD_REQUEST));

        account.updateCompanyAccountInfo(updateCompanyAccountInfoReqDto);

        return UpdateCompanyAccountInfoResDto.builder()
                .message("수정 완료되었습니다.")
                .build();
    }
}
