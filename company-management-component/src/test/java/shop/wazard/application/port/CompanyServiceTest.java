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
import shop.wazard.application.port.out.company.LoadCompanyPort;
import shop.wazard.application.port.out.company.SaveCompanyPort;
import shop.wazard.application.port.out.company.UpdateCompanyPort;
import shop.wazard.dto.RegisterCompanyReqDto;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyServiceImpl.class})
class CompanyServiceTest {

    @Autowired
    CompanyService companyService;
    @MockBean
    LoadCompanyPort loadCompanyPort;
    @MockBean
    UpdateCompanyPort updateCompanyPort;
    @MockBean
    SaveCompanyPort saveCompanyPort;

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
        Mockito.when(loadCompanyPort.findAccountByEmail(registerCompanyReqDto.getEmail()))
                .thenReturn(account);

        // then
        Assertions.assertDoesNotThrow(() -> companyService.registerCompany(registerCompanyReqDto));
    }

}
