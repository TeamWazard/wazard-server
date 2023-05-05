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
import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.UpdateCompanyReqDto;

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
    @DisplayName("고용주 - 업장 수정 - 성공")
    public void updateCompanyInfoSuccess() throws Exception {
        //given
        UpdateCompanyReqDto updateCompanyReqDto = UpdateCompanyReqDto.builder()
                .id(Long.valueOf(1))
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
        Mockito.when(loadCompanyPort.findCompanyById(updateCompanyReqDto.getId())).thenReturn(company);
        company.getCompanyInfo().updateCompanyInfo(updateCompanyReqDto);
        companyService.updateCompanyInfo(updateCompanyReqDto);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyName(), updateCompanyReqDto.getCompanyName()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyAddress(), updateCompanyReqDto.getCompanyAddress()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyContact(), updateCompanyReqDto.getCompanyContact()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getSalaryDate(), updateCompanyReqDto.getSalaryDate())
        );
    }

}
