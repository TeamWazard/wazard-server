package shop.wazard.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForCompany;
import shop.wazard.application.domain.Company;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.AccountForCompanyPort;
import shop.wazard.application.port.out.CompanyPort;
import shop.wazard.application.port.out.RosterForCompanyPort;
import shop.wazard.dto.*;

@Transactional
@Service
@RequiredArgsConstructor
class CompanyServiceImpl implements CompanyService {

    private final CompanyPort companyPort;
    private final AccountForCompanyPort accountForCompanyPort;
    private final RosterForCompanyPort rosterForCompanyPort;

    @Override
    public RegisterCompanyResDto registerCompany(RegisterCompanyReqDto registerCompanyReqDto) {
        AccountForCompany accountForCompany =
                accountForCompanyPort.findAccountByEmail(registerCompanyReqDto.getEmail());
        accountForCompany.checkIsEmployer();
        companyPort.saveCompany(
                accountForCompany.getEmail(), Company.createCompany(registerCompanyReqDto));
        return RegisterCompanyResDto.builder().message("업장 등록이 완료되었습니다.").build();
    }

    @Override
    public UpdateCompanyInfoResDto updateCompanyInfo(
            UpdateCompanyInfoReqDto updateCompanyInfoReqDto) {
        AccountForCompany accountForCompany =
                accountForCompanyPort.findAccountByEmail(updateCompanyInfoReqDto.getEmail());
        accountForCompany.checkIsEmployer();
        Company company = companyPort.findCompanyById(updateCompanyInfoReqDto.getCompanyId());
        company.getCompanyInfo().updateCompanyInfo(updateCompanyInfoReqDto);
        companyPort.updateCompanyInfo(company);
        return UpdateCompanyInfoResDto.builder().message("업장 정보 수정이 완료되었습니다.").build();
    }

    @Override
    public DeleteCompanyResDto deleteCompany(DeleteCompanyReqDto deleteCompanyReqDto) {
        AccountForCompany accountForCompany =
                accountForCompanyPort.findAccountByEmail(deleteCompanyReqDto.getEmail());
        accountForCompany.checkIsEmployer();
        rosterForCompanyPort.deleteRoster(deleteCompanyReqDto.getCompanyId());
        return DeleteCompanyResDto.builder().message("업장이 삭제되었습니다.").build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetOwnedCompanyResDto> getOwnedCompanyList(
            Long accountId, GetOwnedCompanyReqDto getOwnedCompanyReqDto) {
        AccountForCompany accountForCompany =
                accountForCompanyPort.findAccountByEmail(getOwnedCompanyReqDto.getEmail());
        accountForCompany.checkIsEmployer();
        return rosterForCompanyPort.getOwnedCompanyList(accountId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetBelongedCompanyResDto> getBelongedCompanyList(
            Long accountId, GetBelongedCompanyReqDto getBelongedCompanyReqDto) {
        AccountForCompany accountForCompany =
                accountForCompanyPort.findAccountByEmail(getBelongedCompanyReqDto.getEmail());
        accountForCompany.checkIsEmployee();
        return rosterForCompanyPort.getBelongedCompanyList(accountId);
    }
}
