package shop.wazard.application.port.in;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.adapter.out.persistence.Account;
import shop.wazard.application.port.out.LoadAccountPort;
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
    @MockBean
    private LoadAccountPort loadAccountPort;

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

        // when
        Account account = loadAccountPort.findByEmail(updateCompanyAccountInfoReqDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Mockito.doNothing().when(account.updateCompanyAccountInfo(updateCompanyAccountInfoReqDto));

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(updateCompanyAccountInfoResDto.getMessage(), "수정되었습니다.")
        );
    }

}
