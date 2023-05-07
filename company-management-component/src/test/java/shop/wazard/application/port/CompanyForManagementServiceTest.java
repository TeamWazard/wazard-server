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
import shop.wazard.application.port.domain.AccountForManagement;
import shop.wazard.application.port.domain.CompanyForManagement;
import shop.wazard.application.port.domain.CompanyInfo;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.LoadAccountForCompanyManagementPort;
import shop.wazard.application.port.out.LoadCompanyPort;
import shop.wazard.application.port.out.SaveCompanyPort;
import shop.wazard.application.port.out.UpdateCompanyPort;
import shop.wazard.dto.DeleteCompanyReqDto;
import shop.wazard.dto.DeleteCompanyResDto;
import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.UpdateCompanyInfoReqDto;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyServiceImpl.class})
class CompanyForManagementServiceTest {

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
        AccountForManagement accountForManagement = AccountForManagement.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();
        CompanyForManagement companyForManagement = CompanyForManagement.builder()
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
                .thenReturn(accountForManagement);

        // then
        Assertions.assertDoesNotThrow(() -> companyService.registerCompany(registerCompanyReqDto));
    }

    @Test
    @DisplayName("고용주 - 업장 정보 수정 - 성공")
    public void updateCompanyInfoSuccess() throws Exception {
        //given
        UpdateCompanyInfoReqDto updateCompanyInfoReqDto = UpdateCompanyInfoReqDto.builder()
                .companyId(1L)
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
        CompanyForManagement companyForManagement = CompanyForManagement.builder().companyInfo(companyInfo).build();

        //when
        Mockito.when(loadCompanyPort.findCompanyById(anyLong())).thenReturn(companyForManagement);
        companyForManagement.getCompanyInfo().updateCompanyInfo(updateCompanyInfoReqDto);
        companyService.updateCompanyInfo(updateCompanyInfoReqDto);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(companyForManagement.getCompanyInfo().getCompanyName(), updateCompanyInfoReqDto.getCompanyName()),
                () -> Assertions.assertEquals(companyForManagement.getCompanyInfo().getCompanyAddress(), updateCompanyInfoReqDto.getCompanyAddress()),
                () -> Assertions.assertEquals(companyForManagement.getCompanyInfo().getCompanyContact(), updateCompanyInfoReqDto.getCompanyContact()),
                () -> Assertions.assertEquals(companyForManagement.getCompanyInfo().getSalaryDate(), updateCompanyInfoReqDto.getSalaryDate())
        );
    }

    @Test
    @DisplayName("고용주 - 업장 삭제 - 성공")
    public void deleteCompanySuccess() throws Exception {
        // given
        DeleteCompanyReqDto deleteCompanyReqDto = DeleteCompanyReqDto.builder()
                .email("test@email.com")
                .companyId(1L)
                .build();
        DeleteCompanyResDto deleteCompanyResDto = DeleteCompanyResDto.builder()
                .message("삭제되었습니다.")
                .build();
        
        //when
        DeleteCompanyResDto result = companyService.deleteCompany(deleteCompanyReqDto);

        //then
        Assertions.assertEquals(result.getMessage(), deleteCompanyResDto.getMessage());
    }

}
