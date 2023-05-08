package shop.wazard.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.domain.AccountForManagement;
import shop.wazard.application.domain.CompanyForManagement;
import shop.wazard.application.domain.CompanyInfo;
import shop.wazard.application.port.in.CompanyManagementService;
import shop.wazard.application.port.out.AccountForCompanyManagementPort;
import shop.wazard.application.port.out.CompanyForManagementPort;
import shop.wazard.application.port.out.RosterForCompanyManagementPort;
import shop.wazard.dto.DeleteCompanyReqDto;
import shop.wazard.dto.RegisterCompanyReqDto;
import shop.wazard.dto.UpdateCompanyInfoReqDto;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyManagementServiceImpl.class})
class CompanyForManagementServiceTest {

    @Autowired
    private CompanyManagementService companyManagementService;
    @MockBean
    private CompanyForManagementPort companyForManagementPort;
    @MockBean
    private AccountForCompanyManagementPort accountForCompanyManagementPort;
    @MockBean
    private RosterForCompanyManagementPort rosterForCompanyManagementPort;

    @Test
    @DisplayName("고용주 - 업장 등록 - 성공")
    public void registerCompanySuccess() throws Exception {
        // given
        RegisterCompanyReqDto registerCompanyReqDto = RegisterCompanyReqDto.builder()
                .email("test@email.com")
                .companyName("테스트 이름")
                .companyAddress("테스트 주소")
                .companyContact("02-123-1234")
                .salaryDate(10)
                .build();
        AccountForManagement accountForManagement = setDefaultAccountForManagement();
        CompanyForManagement companyForManagement = setDefaultCompanyForManagement();

        // when
        Mockito.when(accountForCompanyManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForManagement);

        // then
        Assertions.assertDoesNotThrow(() -> companyManagementService.registerCompany(registerCompanyReqDto));
    }

    @Test
    @DisplayName("고용주 - 업장 정보 수정 - 성공")
    public void updateCompanyInfoSuccess() throws Exception {
        //given
        UpdateCompanyInfoReqDto updateCompanyInfoReqDto = UpdateCompanyInfoReqDto.builder()
                .companyId(1L)
                .email("test@email.com")
                .companyName("수정 테스트 이름")
                .companyAddress("수정 테스트 주소")
                .companyContact("02-123-1234")
                .salaryDate(10)
                .build();
        AccountForManagement accountForManagement = setDefaultAccountForManagement();
        CompanyForManagement companyForManagement = setDefaultCompanyForManagement();

        //when
        Mockito.when(accountForCompanyManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForManagement);
        Mockito.when(companyForManagementPort.findCompanyById(anyLong()))
                .thenReturn(companyForManagement);
        companyManagementService.updateCompanyInfo(updateCompanyInfoReqDto);

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
        AccountForManagement accountForManagement = setDefaultAccountForManagement();

        //when
        Mockito.when(accountForCompanyManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForManagement);

        //then
        Assertions.assertDoesNotThrow(() -> companyManagementService.deleteCompany(deleteCompanyReqDto));
    }

    private AccountForManagement setDefaultAccountForManagement() {
        return AccountForManagement.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();
    }

    private CompanyForManagement setDefaultCompanyForManagement() {
        return CompanyForManagement.builder()
                .companyInfo(CompanyInfo.builder()
                        .companyName("테스트")
                        .companyAddress("테스트 주소")
                        .companyContact("02-123-1234")
                        .salaryDate(10)
                        .build())
                .build();
    }

}
