package shop.wazard.application.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.account.LoadAccountPort;
import shop.wazard.application.port.out.company.LoadCompanyPort;
import shop.wazard.application.port.out.company.SaveCompanyPort;
import shop.wazard.application.port.out.company.UpdateCompanyPort;
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
    private final LoadAccountPort loadAccountPort;

    @Override
    public RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        Account account = loadAccountPort.findAccountByEmail(registerCompanyReqDto.getEmail());
        if (!account.isEmployer()) {
            throw new RegisterPermissionDenied(StatusEnum.REGISTER_COMPANY_DENIED.getMessage());
        }
        saveCompanyPort.saveCompany(Company.createCompany(registerCompanyReqDto));
        return RegisterCompanyResDto.builder()
                .message("업장 등록이 완료되었습니다.")
                .build();
    }

}
