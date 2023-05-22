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
import shop.wazard.application.domain.AccountForMyPage;
import shop.wazard.application.port.in.MyPageService;
import shop.wazard.application.port.out.AccountForMyPagePort;
import shop.wazard.application.port.out.CompanyForMyPagePort;
import shop.wazard.application.port.out.RosterForMyPagePort;
import shop.wazard.dto.GetPastWorkplaceReqDto;
import shop.wazard.dto.GetPastWorkplaceResDto;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MyPageServiceImpl.class})
class MyPageServiceImplTest {

    @Autowired
    private MyPageService myPageService;
    @MockBean
    private AccountForMyPagePort accountForMyPagePort;
    @MockBean
    private RosterForMyPagePort rosterForMyPagePort;
    @MockBean
    private CompanyForMyPagePort companyForMyPagePort;

    @Test
    @DisplayName("근무자 - 과거 근무지 조회 - 성공")
    void getPastWorkplaceService() throws Exception {
        // given
        GetPastWorkplaceReqDto getPastWorkplaceReqDto = GetPastWorkplaceReqDto.builder()
                .email("test@email.com")
                .build();
        AccountForMyPage accountForAttendance = AccountForMyPage.builder()
                .id(1L)
                .roles("EMPLOYEE")
                .build();
        List<GetPastWorkplaceResDto> getPastWorkplaceResDtoList = setDefaultPastWorkPlaceList();

        // when
        Mockito.when(accountForMyPagePort.findAccountByEmail(anyString()))
                .thenReturn(accountForAttendance);
        Mockito.when(myPageService.getPastWorkplaces(getPastWorkplaceReqDto))
                .thenReturn(getPastWorkplaceResDtoList);
        List<GetPastWorkplaceResDto> result = myPageService.getPastWorkplaces(getPastWorkplaceReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.get(0).getCompanyId(), getPastWorkplaceResDtoList.get(0).getCompanyId()),
                () -> Assertions.assertEquals(result.get(0).getCompanyName(), getPastWorkplaceResDtoList.get(0).getCompanyName()),
                () -> Assertions.assertEquals(result.get(1).getCompanyId(), getPastWorkplaceResDtoList.get(1).getCompanyId()),
                () -> Assertions.assertEquals(result.get(1).getCompanyName(), getPastWorkplaceResDtoList.get(1).getCompanyName())
        );
    }

    private List<GetPastWorkplaceResDto> setDefaultPastWorkPlaceList() {
        List<GetPastWorkplaceResDto> getPastWorkplaceResDtoList = new ArrayList<>();
        GetPastWorkplaceResDto getPastWorkplaceResDto1 = GetPastWorkplaceResDto.builder()
                .companyId(1L)
                .companyName("test1")
                .companyContact("02-111-1111")
                .companyAddress("testAddress1")
                .logoImageUrl("testImg1")
                .build();
        GetPastWorkplaceResDto getPastWorkplaceResDto2 = GetPastWorkplaceResDto.builder()
                .companyId(2L)
                .companyName("test2")
                .companyContact("02-222-2222")
                .companyAddress("testAddress2")
                .logoImageUrl("testImg2")
                .build();
        getPastWorkplaceResDtoList.add(getPastWorkplaceResDto1);
        getPastWorkplaceResDtoList.add(getPastWorkplaceResDto2);
        return getPastWorkplaceResDtoList;
    }

}