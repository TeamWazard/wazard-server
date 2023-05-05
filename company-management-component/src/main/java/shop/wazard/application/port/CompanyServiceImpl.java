package shop.wazard.application.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.LoadAccountForCompanyManagementPort;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.RegisterCompanyResDto;
import shop.wazard.dto.UpdateCompanyReqDto;
import shop.wazard.dto.UpdateCompanyResDto;
import shop.wazard.exception.RegisterPermissionDenied;
import shop.wazard.util.exception.StatusEnum;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CompanyServiceImpl implements CompanyService {

    private final LoadCompanyPort loadCompanyPort;
    private final SaveCompanyPort saveCompanyPort;
    private final UpdateCompanyPort updateCompanyPort;
    private final LoadAccountForCompanyManagementPort loadAccountForCompanyManagementPort;

    @Override
    public RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        Account account = loadAccountForCompanyManagementPort.findAccountByEmail(registerCompanyReqDto.getEmail());
        if (!account.isEmployer()) {
            throw new RegisterPermissionDenied(StatusEnum.REGISTER_COMPANY_DENIED.getMessage());
        }
        saveCompanyPort.saveCompany(account.getEmail(), Company.createCompany(registerCompanyReqDto));
        return RegisterCompanyResDto.builder()
                .message("업장 등록이 완료되었습니다.")
                .build();
    }

    @Override
    public UpdateCompanyResDto updateCompanyInfo(UpdateCompanyReqDto updateCompanyReqDto) {
        Company company = loadCompanyPort.findCompanyById(updateCompanyReqDto.getId());
        company.getCompanyInfo().updateCompanyInfo(updateCompanyReqDto);
        updateCompanyPort.updateCompanyInfo(company);
        return UpdateCompanyResDto.builder()
                .message("업장 수정이 완료되었습니다.")
                .build();
    }

}
