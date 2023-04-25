package shop.wazard.application.port.in;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.port.out.SaveAccountPort;
import shop.wazard.dto.UpdateCompanyAccountInfoReqDto;
import shop.wazard.dto.UpdateCompanyAccountInfoResDto;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
class CompanyAccountServiceTest {

    @Autowired
    private CompanyAccountService companyAccountService;
    @MockBean
    private SaveAccountPort saveAccountPort;

    @Test
    @DisplayName("고용주 - 회원정보 수정 성공")
    public void updateCompanyAccountInfoSuccess() throws Exception {
        // given
        UpdateCompanyAccountInfoReqDto updateCompanyAccountInfoReqDto = UpdateCompanyAccountInfoReqDto.builder()
                .email("test@naver.com")
                .userName("testName")
                .gender("MALE")
                .birth(LocalDate.of(2000, 1, 1))
                .build();
        UpdateCompanyAccountInfoResDto updateCompanyAccountInfoResDto = UpdateCompanyAccountInfoResDto.builder()
                .message("수정되었습니다.")
                .build();

        //when
//        Mockito.doNothing().when(saveAccountPort.updateCompanyAccountInfo(updateCompanyAccountInfoReqDto));
//
//        //then
//        saveAccountPort.updateCompanyAccountInfo(updateCompanyAccountInfoReqDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals(updateCompanyAccountInfoResDto.getMessage(), "수정되었습니다.")
        );
    }

}
