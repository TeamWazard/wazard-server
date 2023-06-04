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
import shop.wazard.application.domain.AccountForCompany;
import shop.wazard.application.domain.Company;
import shop.wazard.application.domain.CompanyInfo;
import shop.wazard.application.port.in.CompanyService;
import shop.wazard.application.port.out.AccountForCompanyPort;
import shop.wazard.application.port.out.CompanyPort;
import shop.wazard.application.port.out.RosterForCompanyPort;
import shop.wazard.dto.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyServiceImpl.class})
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;
    @MockBean
    private CompanyPort companyPort;
    @MockBean
    private AccountForCompanyPort accountForCompanyPort;
    @MockBean
    private RosterForCompanyPort rosterForCompanyPort;

    @Test
    @DisplayName("고용주 - 업장 등록 - 성공")
    public void registerCompanySuccess() throws Exception {
        // given
        RegisterCompanyReqDto registerCompanyReqDto = RegisterCompanyReqDto.builder()
                .email("test@email.com")
                .companyName("테스트 이름")
                .zipCode(100)
                .companyAddress("테스트 주소")
                .companyDetailAddress("상세 주소")
                .companyContact("02-123-1234")
                .businessType("업종")
                .salaryDate(10)
                .build();
        AccountForCompany accountForCompany = setDefaultEmployerAccountForManagement();
        Company company = setDefaultCompanyForManagement();

        // when
        Mockito.when(accountForCompanyPort.findAccountByEmail(anyString()))
                .thenReturn(accountForCompany);

        // then
        Assertions.assertDoesNotThrow(() -> companyService.registerCompany(registerCompanyReqDto));
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
        AccountForCompany accountForCompany = setDefaultEmployerAccountForManagement();
        Company company = setDefaultCompanyForManagement();

        //when
        Mockito.when(accountForCompanyPort.findAccountByEmail(anyString()))
                .thenReturn(accountForCompany);
        Mockito.when(companyPort.findCompanyById(anyLong()))
                .thenReturn(company);
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
        DeleteCompanyReqDto deleteCompanyReqDto = DeleteCompanyReqDto.builder()
                .email("test@email.com")
                .companyId(1L)
                .build();
        AccountForCompany accountForCompany = setDefaultEmployerAccountForManagement();

        //when
        Mockito.when(accountForCompanyPort.findAccountByEmail(anyString()))
                .thenReturn(accountForCompany);

        //then
        Assertions.assertDoesNotThrow(() -> companyService.deleteCompany(deleteCompanyReqDto));
    }

    @Test
    @DisplayName("고용주 - 운영 업장 리스트 조회 - 성공")
    public void getOwnedCompanyList() throws Exception {
        // given
        GetOwnedCompanyReqDto getOwnedCompanyReqDto = GetOwnedCompanyReqDto.builder()
                .email("test@gmail.com")
                .build();
        AccountForCompany accountForCompany = setDefaultEmployerAccountForManagement();
        List<GetOwnedCompanyResDto> getOwnedCompanyResDtoList = setDefaultOwnedCompanyList();

        // when
        Mockito.when(accountForCompanyPort.findAccountByEmail(anyString()))
                .thenReturn(accountForCompany);
        Mockito.when(rosterForCompanyPort.getOwnedCompanyList(anyLong()))
                .thenReturn(getOwnedCompanyResDtoList);
        List<GetOwnedCompanyResDto> result = companyService.getOwnedCompanyList(1L,getOwnedCompanyReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.get(0).getCompanyName(),getOwnedCompanyResDtoList.get(0).getCompanyName()),
                () -> Assertions.assertEquals(result.get(1).getCompanyName(),getOwnedCompanyResDtoList.get(1).getCompanyName()),
                () -> Assertions.assertEquals(result.get(2).getCompanyName(),getOwnedCompanyResDtoList.get(2).getCompanyName())
        );
    }

    @Test
    @DisplayName("근무자 - 소속 업장 리스트 조회 - 성공")
    void getBelongedCompany() throws Exception {
        // given
        GetBelongedCompanyReqDto getBelongedCompanyReqDto = GetBelongedCompanyReqDto.builder()
                .email("test@email.com")
                .build();
        AccountForCompany accountForCompany = setDefaultEmployeeAccountForManagement();
        List<GetBelongedCompanyResDto> getBelongedCompanyResDtoList = setDefaultBelongedCompanyList();

        // when
        Mockito.when(accountForCompanyPort.findAccountByEmail(anyString()))
                .thenReturn(accountForCompany);
        Mockito.when(rosterForCompanyPort.getBelongedCompanyList(anyLong()))
                .thenReturn(getBelongedCompanyResDtoList);
        List<GetBelongedCompanyResDto> result = companyService.getBelongedCompanyList(1L, getBelongedCompanyReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(getBelongedCompanyResDtoList.get(0).getCompanyName(), result.get(0).getCompanyName()),
                () -> Assertions.assertEquals(getBelongedCompanyResDtoList.get(1).getCompanyName(), result.get(1).getCompanyName()),
                () -> Assertions.assertEquals(getBelongedCompanyResDtoList.get(2).getCompanyName(), result.get(2).getCompanyName())
        );
    }

    private AccountForCompany setDefaultEmployerAccountForManagement() {
        return AccountForCompany.builder()
                .id(1L)
                .roles("EMPLOYER")
                .build();
    }

    private AccountForCompany setDefaultEmployeeAccountForManagement() {
        return AccountForCompany.builder()
                .id(1L)
                .roles("EMPLOYEE")
                .build();
    }

    private Company setDefaultCompanyForManagement() {
        return Company.builder()
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

    private List<GetBelongedCompanyResDto> setDefaultBelongedCompanyList() {
        List<GetBelongedCompanyResDto> getBelongedCompanyResDto = new ArrayList<>();
        GetBelongedCompanyResDto getBelongedCompanyResDto1 = GetBelongedCompanyResDto.builder()
                .companyName("companyName1")
                .companyAddress("companyAddress1")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test1.com")
                .build();
        GetBelongedCompanyResDto getBelongedCompanyResDto2 = GetBelongedCompanyResDto.builder()
                .companyName("companyName2")
                .companyAddress("companyAddress2")
                .companyContact("02-222-2222")
                .salaryDate(2)
                .logoImageUrl("www.test2.com")
                .build();
        GetBelongedCompanyResDto getBelongedCompanyResDto3 = GetBelongedCompanyResDto.builder()
                .companyName("companyName3")
                .companyAddress("companyAddress3")
                .companyContact("02-333-3333")
                .salaryDate(3)
                .logoImageUrl("www.test3.com")
                .build();
        getBelongedCompanyResDto.add(getBelongedCompanyResDto1);
        getBelongedCompanyResDto.add(getBelongedCompanyResDto2);
        getBelongedCompanyResDto.add(getBelongedCompanyResDto3);
        return getBelongedCompanyResDto;
    }

}
