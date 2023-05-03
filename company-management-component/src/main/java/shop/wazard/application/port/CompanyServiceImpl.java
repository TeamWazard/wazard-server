package shop.wazard.application.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.RegisterCompanyResDto;
import shop.wazard.exception.RegisterPermissionDenied;
import shop.wazard.util.exception.StatusEnum;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CompanyServiceImpl implements CompanyService {

    private final LoadCompanyPort loadCompanyPort;
    private final SaveCompanyPort saveCompanyPort;
    private final UpdateCompanyPort updateCompanyPort;

    @Override
    @Transactional
    public RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        Account account = loadCompanyPort.findAccountByEmail(registerCompanyReqDto.getEmail());
        if (!account.isEmployer()) {
            throw new RegisterPermissionDenied(StatusEnum.REGISTER_COMPANY_DENIED.getMessage());
        }
        saveCompanyPort.saveCompany(Company.createCompany(registerCompanyReqDto), account);
        return RegisterCompanyResDto.builder()
                .message("업장 등록이 완료되었습니다.")
                .build();
    }

}
