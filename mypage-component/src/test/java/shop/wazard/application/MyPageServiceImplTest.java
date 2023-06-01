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
import shop.wazard.application.domain.CompanyInfoForMyPage;
import shop.wazard.application.domain.WorkRecordForMyPage;
import shop.wazard.application.port.in.MyPageService;
import shop.wazard.application.port.out.AccountForMyPagePort;
import shop.wazard.application.port.out.CompanyForMyPagePort;
import shop.wazard.application.port.out.RosterForMyPagePort;
import shop.wazard.application.port.out.WorkRecordForMyPagePort;
import shop.wazard.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
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
    @MockBean
    private WorkRecordForMyPagePort workRecordForMyPagePort;

    @Test
    @DisplayName("근무자 - 과거 근무지 조회 - 성공")
    void getPastWorkplaceService() throws Exception {
        // given
        GetPastWorkplaceReqDto getPastWorkplaceReqDto = GetPastWorkplaceReqDto.builder()
                .email("test@email.com")
                .build();
        AccountForMyPage accountForAttendance = setDefaultEmployeeAccountForManagement();
        List<GetPastWorkplaceResDto> getPastWorkplaceResDtoList = setDefaultPastWorkPlaceList();

        // when
        Mockito.when(accountForMyPagePort.findAccountByEmail(anyString()))
                .thenReturn(accountForAttendance);
        Mockito.when(companyForMyPagePort.getPastWorkplaces(anyLong()))
                .thenReturn(getPastWorkplaceResDtoList);
        List<GetPastWorkplaceResDto> result = myPageService.getPastWorkplaces(getPastWorkplaceReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(getPastWorkplaceResDtoList.get(0).getCompanyName(), result.get(0).getCompanyName()),
                () -> Assertions.assertEquals(getPastWorkplaceResDtoList.get(1).getCompanyName(), result.get(1).getCompanyName())
        );
    }

    @Test
    @DisplayName("근로자 - 과거 근무 기록 상세 조회 - 성공")
    void getPastWorkRecord() throws Exception {
        // given
        GetMyPastWorkRecordReqDto getMyPastWorkRecordReqDto = GetMyPastWorkRecordReqDto.builder()
                .email("test@email.com")
                .companyId(2L)
                .build();
        AccountForMyPage accountForAttendance = setDefaultEmployeeAccountForManagement();
        CompanyInfoForMyPage companyInfoForMyPage = CompanyInfoForMyPage.builder()
                .companyName("testName")
                .companyAddress("testAddress")
                .companyContact("02-111-1234")
                .logoImageUrl("testLogoImage")
                .build();
        WorkRecordForMyPage workRecordForMyPage = WorkRecordForMyPage.builder()
                .absentCount(1)
                .tardyCount(1)
                .startWorkingDate(LocalDate.of(2023, 5, 20))
                .endWorkingDate(LocalDate.of(2023, 5, 23))
                .workDayCount(10)
                .build();
        GetMyPastWorkRecordResDto getMyPastWorkRecordResDto = GetMyPastWorkRecordResDto.builder()
                .companyName("testName")
                .companyAddress("testAddress")
                .companyContact("02-111-1234")
                .companyLogoImage("testLogoImage")
                .tardyCount(1)
                .absentCount(1)
                .workScore(4)
                .startWorkingDate(LocalDate.of(2023, 5, 20))
                .endWorkingDate(LocalDate.of(2023, 5, 23))
                .build();

        // when
        Mockito.when(accountForMyPagePort.findAccountByEmail(anyString()))
                .thenReturn(accountForAttendance);
        Mockito.when(companyForMyPagePort.findPastCompanyInfo(anyLong(), anyLong()))
                .thenReturn(companyInfoForMyPage);
        Mockito.when(workRecordForMyPagePort.getMyPastWorkRecord(anyLong(), anyLong()))
                .thenReturn(workRecordForMyPage);
        GetMyPastWorkRecordResDto result = myPageService.getMyPastWorkingRecord(getMyPastWorkRecordReqDto);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getCompanyName(), result.getCompanyName()),
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getCompanyAddress(), result.getCompanyAddress()),
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getCompanyContact(), result.getCompanyContact()),
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getCompanyLogoImage(), result.getCompanyLogoImage()),
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getStartWorkingDate(), result.getStartWorkingDate()),
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getEndWorkingDate(), result.getEndWorkingDate()),
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getAbsentCount(), result.getAbsentCount()),
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getTardyCount(), result.getTardyCount()),
                () -> Assertions.assertEquals(getMyPastWorkRecordResDto.getWorkScore(), result.getWorkScore())
        );
    }

    @Test
    @DisplayName("근로자 - 나의 태도 점수 평균 조회 - 성공")
    void getMyAttitudeScore() throws Exception {
        // given
        GetMyAttitudeScoreReqDto getMyAttitudeScoreReqDto = GetMyAttitudeScoreReqDto.builder()
                .email("test@email.com")
                .companyId(2L)
                .build();
        AccountForMyPage accountForAttendance = setDefaultEmployeeAccountForManagement();
        List<WorkRecordForMyPage> workRecordForMyPageList = setDefaultMyPastWorkRecordList();

        // when
        Mockito.when(accountForMyPagePort.findAccountByEmail(anyString()))
                .thenReturn(accountForAttendance);
        Mockito.when(workRecordForMyPagePort.getMyTotalPastRecord(anyLong()))
                .thenReturn(workRecordForMyPageList);
        GetMyAttitudeScoreResDto result = myPageService.getMyAttitudeScore(getMyAttitudeScoreReqDto);

        // then
        // + 값 직접 확인
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getMyAttitudeScore(), 5.5)
        );
    }

    private List<WorkRecordForMyPage> setDefaultMyPastWorkRecordList() {
        List<WorkRecordForMyPage> workRecordForMyPageList = new ArrayList<>();
        // 0.4 점
        WorkRecordForMyPage workRecordForMyPage1 = WorkRecordForMyPage.builder()
                .absentCount(1)
                .tardyCount(2)
                .workDayCount(10)
                .build();
        // 0.8 점
        WorkRecordForMyPage workRecordForMyPage2 = WorkRecordForMyPage.builder()
                .absentCount(0)
                .tardyCount(2)
                .workDayCount(10)
                .build();
        workRecordForMyPageList.add(workRecordForMyPage1);
        workRecordForMyPageList.add(workRecordForMyPage2);
        return workRecordForMyPageList;
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

    private AccountForMyPage setDefaultEmployeeAccountForManagement() {
        return AccountForMyPage.builder()
                .id(1L)
                .roles("EMPLOYEE")
                .build();
    }


}