package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForManagement;
import shop.wazard.application.domain.CompanyForManagement;
import shop.wazard.application.port.in.CompanyManagementService;
import shop.wazard.application.port.out.*;
import shop.wazard.dto.*;
import shop.wazard.exception.NotAuthorizedException;
import shop.wazard.util.exception.StatusEnum;

@Service
@RequiredArgsConstructor
class CompanyManagementServiceImpl implements CompanyManagementService {

    private final LoadCompanyForManagementPort loadCompanyForManagementPort;
    private final SaveCompanyForManagementPort saveCompanyForManagementPort;
    private final UpdateCompanyPort updateCompanyPort;
    private final LoadAccountForCompanyManagementPort loadAccountForCompanyManagementPort;
    private final UpdateCompanyAccountRelForCompanyManagementPort updateCompanyAccountRelForCompanyManagementPort;

    @Transactional
    @Override
    public RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        AccountForManagement accountForManagement = loadAccountForCompanyManagementPort.findAccountByEmail(registerCompanyReqDto.getEmail());
        if (!accountForManagement.isEmployer()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
        saveCompanyForManagementPort.saveCompany(accountForManagement.getEmail(), CompanyForManagement.createCompany(registerCompanyReqDto));
        return RegisterCompanyResDto.builder()
                .message("업장 등록이 완료되었습니다.")
                .build();
    }

    @Transactional
    @Override
    public UpdateCompanyInfoResDto updateCompanyInfo(UpdateCompanyInfoReqDto updateCompanyInfoReqDto) {
        AccountForManagement accountForManagement = loadAccountForCompanyManagementPort.findAccountByEmail(updateCompanyInfoReqDto.getEmail());
        if (!accountForManagement.isEmployer()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
        CompanyForManagement companyForManagement = loadCompanyForManagementPort.findCompanyById(updateCompanyInfoReqDto.getCompanyId());
        companyForManagement.getCompanyInfo().updateCompanyInfo(updateCompanyInfoReqDto);
        updateCompanyPort.updateCompanyInfo(companyForManagement);
        return UpdateCompanyInfoResDto.builder()
                .message("업장 수정이 완료되었습니다.")
                .build();
    }

    @Transactional
    @Override
    public DeleteCompanyResDto deleteCompany(DeleteCompanyReqDto deleteCompanyReqDto) {
        AccountForManagement accountForManagement = loadAccountForCompanyManagementPort.findAccountByEmail(deleteCompanyReqDto.getEmail());
        if (!accountForManagement.isEmployer()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
        updateCompanyAccountRelForCompanyManagementPort.deleteCompanyAccountRel(deleteCompanyReqDto.getCompanyId());
        return DeleteCompanyResDto.builder()
                .message("삭제되었습니다.")
                .build();
    }

}
