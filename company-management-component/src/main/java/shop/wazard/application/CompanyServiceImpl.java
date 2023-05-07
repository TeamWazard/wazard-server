package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.domain.AccountForManagement;
import shop.wazard.application.port.domain.CompanyForManagement;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.*;
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
    private final UpdateCompanyAccountRelForCompanyManagementPort updateCompanyAccountRelForCompanyManagementPort;

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
    @Transactional
    public DeleteCompanyResDto deleteCompany(DeleteCompanyReqDto deleteCompanyReqDto) {
        AccountForManagement accountForManagement = loadAccountForCompanyManagementPort.findAccountByEmail(deleteCompanyReqDto.getEmail());
        if (!accountForManagement.isEmployer()) {
            throw new RegisterPermissionDenied(StatusEnum.REGISTER_COMPANY_DENIED.getMessage());
        }
        updateCompanyPort.deleteCompany(deleteCompanyReqDto.getCompanyId());
        updateCompanyAccountRelForCompanyManagementPort.deleteCompanyAccountRel(deleteCompanyReqDto.getCompanyId());
        return DeleteCompanyResDto.builder()
                .message("삭제되었습니다.")
                .build();
    }
}
