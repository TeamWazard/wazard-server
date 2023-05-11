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
import shop.wazard.dto.*;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("고용주 - 운영 업장 리스트 조회 - 성공")
    public void getOwnedCompanyList() throws Exception {
        // given
        GetOwnedCompanyReqDto getOwnedCompanyReqDto = GetOwnedCompanyReqDto.builder()
                .email("test@gmail.com")
                .build();
        AccountForManagement accountForManagement = setDefaultAccountForManagement();
        List<GetOwnedCompanyResDto> getOwnedCompanyResDtoList = setDefaultOwnedCompanyList();

        // when
        Mockito.when(accountForCompanyManagementPort.findAccountByEmail(anyString()))
                .thenReturn(accountForManagement);
        Mockito.when(rosterForCompanyManagementPort.getOwnedCompanyList(anyLong()))
                .thenReturn(getOwnedCompanyResDtoList);
        List<GetOwnedCompanyResDto> result = companyManagementService.getOwnedCompanyList(1L,getOwnedCompanyReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.get(0).getCompanyName(),getOwnedCompanyResDtoList.get(0).getCompanyName()),
                () -> Assertions.assertEquals(result.get(1).getCompanyName(),getOwnedCompanyResDtoList.get(1).getCompanyName()),
                () -> Assertions.assertEquals(result.get(2).getCompanyName(),getOwnedCompanyResDtoList.get(2).getCompanyName())
        );
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

    private List<GetOwnedCompanyResDto> setDefaultOwnedCompanyList() {
        List<GetOwnedCompanyResDto> getOwnedCompanyResDtoList = new ArrayList<>();
        GetOwnedCompanyResDto getOwnedCompanyResDto1 = GetOwnedCompanyResDto.builder()
                .companyName("companyName1")
                .companyAddress("companyAddress1")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test1.com")
                .build();
        GetOwnedCompanyResDto getOwnedCompanyResDto2 = GetOwnedCompanyResDto.builder()
                .companyName("companyName2")
                .companyAddress("companyAddress2")
                .companyContact("02-222-2222")
                .salaryDate(2)
                .logoImageUrl("www.test2.com")
                .build();
        GetOwnedCompanyResDto getOwnedCompanyResDto3 = GetOwnedCompanyResDto.builder()
                .companyName("companyName3")
                .companyAddress("companyAddress3")
                .companyContact("02-333-3333")
                .salaryDate(3)
                .logoImageUrl("www.test3.com")
                .build();
        getOwnedCompanyResDtoList.add(getOwnedCompanyResDto1);
        getOwnedCompanyResDtoList.add(getOwnedCompanyResDto2);
        getOwnedCompanyResDtoList.add(getOwnedCompanyResDto3);
        return getOwnedCompanyResDtoList;
    }

}
