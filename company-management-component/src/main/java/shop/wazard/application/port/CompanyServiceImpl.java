package shop.wazard.application.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.domain.AccountForManagement;
import shop.wazard.application.port.domain.CompanyForManagement;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.LoadAccountForCompanyManagementPort;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.dto.*;
import shop.wazard.exception.RegisterPermissionDenied;
import shop.wazard.util.exception.StatusEnum;

@Service
@RequiredArgsConstructor
class CompanyServiceImpl implements CompanyService {

    private final LoadCompanyPort loadCompanyPort;
    private final SaveCompanyPort saveCompanyPort;
    private final UpdateCompanyPort updateCompanyPort;
    private final LoadAccountForCompanyManagementPort loadAccountForCompanyManagementPort;

    @Transactional
    @Override
    public RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        AccountForManagement accountForManagement = loadAccountForCompanyManagementPort.findAccountByEmail(registerCompanyReqDto.getEmail());
        if (!accountForManagement.isEmployer()) {
            throw new RegisterPermissionDenied(StatusEnum.REGISTER_COMPANY_DENIED.getMessage());
        }
        saveCompanyPort.saveCompany(accountForManagement.getEmail(), CompanyForManagement.createCompany(registerCompanyReqDto));
        return RegisterCompanyResDto.builder()
                .message("업장 등록이 완료되었습니다.")
                .build();
    }

    @Transactional
    @Override
    public UpdateCompanyInfoResDto updateCompanyInfo(UpdateCompanyInfoReqDto updateCompanyInfoReqDto) {
        CompanyForManagement companyForManagement = loadCompanyPort.findCompanyById(updateCompanyInfoReqDto.getCompanyId());
        companyForManagement.getCompanyInfo().updateCompanyInfo(updateCompanyInfoReqDto);
        updateCompanyPort.updateCompanyInfo(companyForManagement);
        return UpdateCompanyInfoResDto.builder()
                .message("업장 수정이 완료되었습니다.")
                .build();
    }

    @Override
    public DeleteCompanyResDto deleteCompany(Long companyId) {
        updateCompanyPort.deleteCompany(companyId);
        return DeleteCompanyResDto.builder()
                .message("삭제되었습니다.")
                .build();
    }
}
