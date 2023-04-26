package shop.wazard.application.port.in;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyAccountService.class})
class CompanyAccountServiceTest {

//    @Autowired
//    private CompanyAccountService companyAccountService;
//    @MockBean
//    private SaveAccountPort saveAccountPort;
//
//    @Test
//    @DisplayName("고용주 회원 정보 수정 단위 테스트")
//    public void updateCompanyAccountInfoSuccess() throws Exception {
//        // given
//        UpdateCompanyAccountInfoReqDto updateCompanyAccountInfoReqDto
//                = new UpdateCompanyAccountInfoReqDto("test@naver.com" ,"testName" , "MALE");
//
//        //when
//        Mockito.when(saveAccountPort.updateCompanyAccountInfo(updateCompanyAccountInfoReqDto)).thenReturn("수정 완료되었습니다.");
//
//        //then
//        UpdateCompanyAccountInfoResDto updateCompanyAccountInfoResDto
//                = saveAccountPort.updateCompanyAccountInfo(updateCompanyAccountInfoReqDto);
//
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(updateCompanyAccountInfoResDto.getMessage(), "수정 완료되었습니다.")
//        );
//    }

}
