package shop.wazard.application.port;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.domain.CompanyInfo;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.LoadAccountForCompanyManagementPort;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.dto.DeleteCompanyResDto;
import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.UpdateCompanyInfoReqDto;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyServiceImpl.class})
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;
    @MockBean
    private LoadCompanyPort loadCompanyPort;
    @MockBean
    private UpdateCompanyPort updateCompanyPort;
    @MockBean
    private SaveCompanyPort saveCompanyPort;
    @MockBean
    private LoadAccountForCompanyManagementPort loadAccountForCompanyManagementPort;

    @Test
    @DisplayName("고용주 - 업장 등록 - 성공")
    public void registerCompanySuccess() throws Exception {
        // given
        RegisterCompanyReqDto registerCompanyReqDto = RegisterCompanyReqDto.builder()
                .email("test@email.com")
                .companyName("테스트이름")
                .companyAddress("테스트 주소")
                .companyContact("02-123-1234")
                .salaryDate(10)
                .build();
        Account account = Account.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();
        Company company = Company.builder()
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName("테스트이름")
                                .companyAddress("테스트 주소")
                                .companyContact("02-123-1234")
                                .salaryDate(10)
                                .build()
                )
                .build();

        // when
        Mockito.when(loadAccountForCompanyManagementPort.findAccountByEmail(registerCompanyReqDto.getEmail()))
                .thenReturn(account);

        // then
        Assertions.assertDoesNotThrow(() -> companyService.registerCompany(registerCompanyReqDto));
    }

    @Test
    @DisplayName("고용주 - 업장 정보 수정 - 성공")
    public void updateCompanyInfoSuccess() throws Exception {
        //given
        UpdateCompanyInfoReqDto updateCompanyInfoReqDto = UpdateCompanyInfoReqDto.builder()
                .id(1L)
                .companyName("수정테스트")
                .companyAddress("테스트 주소")
                .companyContact("02-123-1234")
                .salaryDate(10)
                .build();
        CompanyInfo companyInfo = CompanyInfo.builder()
                .companyName("테스트")
                .companyAddress("테스트 주소")
                .companyContact("02-123-1234")
                .salaryDate(10)
                .build();
        Company company = Company.builder().companyInfo(companyInfo).build();

        //when
        Mockito.when(loadCompanyPort.findCompanyById(anyLong())).thenReturn(company);
        company.getCompanyInfo().updateCompanyInfo(updateCompanyInfoReqDto);
        companyService.updateCompanyInfo(updateCompanyInfoReqDto);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyName(), updateCompanyInfoReqDto.getCompanyName()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyAddress(), updateCompanyInfoReqDto.getCompanyAddress()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyContact(), updateCompanyInfoReqDto.getCompanyContact()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getSalaryDate(), updateCompanyInfoReqDto.getSalaryDate())
        );
    }

    @Test
    @DisplayName("고용주 - 업장 삭제 - 성공")
    public void deleteCompanySuccess() throws Exception {
        // given
        Long companyId = 1L;
        DeleteCompanyResDto deleteCompanyResDto = DeleteCompanyResDto.builder()
                .message("삭제되었습니다.")
                .build();
        
        //when
        DeleteCompanyResDto result = companyService.deleteCompany(companyId);

        //then
        Assertions.assertEquals(result.getMessage(), deleteCompanyResDto.getMessage());
    }

}
