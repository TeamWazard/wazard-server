package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForManagement;
import shop.wazard.application.domain.CompanyForManagement;
import shop.wazard.application.port.in.CompanyManagementService;
import shop.wazard.application.port.out.AccountForCompanyManagementPort;
import shop.wazard.application.port.out.CompanyForManagementPort;
import shop.wazard.application.port.out.RosterForCompanyManagementPort;
import shop.wazard.dto.*;
import shop.wazard.exception.NotAuthorizedException;
import shop.wazard.util.exception.StatusEnum;

import java.util.List;

@Service
@RequiredArgsConstructor
class CompanyManagementServiceImpl implements CompanyManagementService {

    private final CompanyForManagementPort companyForManagementPort;
    private final AccountForCompanyManagementPort accountForCompanyManagementPort;
    private final RosterForCompanyManagementPort rosterForCompanyManagementPort;

    @Transactional
    @Override
    public RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        AccountForManagement accountForManagement = accountForCompanyManagementPort.findAccountByEmail(registerCompanyReqDto.getEmail());
        if (!accountForManagement.isEmployer()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
        companyForManagementPort.saveCompany(accountForManagement.getEmail(), CompanyForManagement.createCompany(registerCompanyReqDto));
        return RegisterCompanyResDto.builder()
                .message("업장 등록이 완료되었습니다.")
                .build();
    }

    @Transactional
    @Override
    public UpdateCompanyInfoResDto updateCompanyInfo(UpdateCompanyInfoReqDto updateCompanyInfoReqDto) {
        AccountForManagement accountForManagement = accountForCompanyManagementPort.findAccountByEmail(updateCompanyInfoReqDto.getEmail());
        if (!accountForManagement.isEmployer()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
        CompanyForManagement companyForManagement = companyForManagementPort.findCompanyById(updateCompanyInfoReqDto.getCompanyId());
        companyForManagement.getCompanyInfo().updateCompanyInfo(updateCompanyInfoReqDto);
        companyForManagementPort.updateCompanyInfo(companyForManagement);
        return UpdateCompanyInfoResDto.builder()
                .message("업장 수정이 완료되었습니다.")
                .build();
    }

    @Transactional
    @Override
    public DeleteCompanyResDto deleteCompany(DeleteCompanyReqDto deleteCompanyReqDto) {
        AccountForManagement accountForManagement = accountForCompanyManagementPort.findAccountByEmail(deleteCompanyReqDto.getEmail());
        if (!accountForManagement.isEmployer()) {
            throw new NotAuthorizedException(StatusEnum.NOT_AUTHORIZED.getMessage());
        }
        companyForManagementPort.deleteCompany(deleteCompanyReqDto.getCompanyId());
        rosterForCompanyManagementPort.deleteRoster(deleteCompanyReqDto.getCompanyId());
        return DeleteCompanyResDto.builder()
                .message("삭제되었습니다.")
                .build();
    }

    @Override
    public List<GetOwnedCompanyResDto> getOwnedCompanyList(Long accountId) {
        return null;
    }
}
