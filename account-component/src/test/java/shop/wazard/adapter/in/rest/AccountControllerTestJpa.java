package shop.wazard.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.wazard.WazardApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(classes = WazardApplication.class)
@AutoConfigureMockMvc
class AccountControllerTestJpa {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("고용주 - 비밀번호 확인 성공")
    @WithMockUser
    public void checkPasswordSuccess() throws Exception {
        // given
        String checkPasswordUrl = "/account/check/{accountId}";
        CheckPasswordReqDto checkPasswordReqDto = CheckPasswordReqDto.builder()
                .password("Test1234!")
                .build();

        //when
        ResultActions result = mockMvc.perform(get(checkPasswordUrl, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkPasswordReqDto)));

        // then
        result.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, "application/json"),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("$.message").value("인증되었습니다.")
        ).andDo(print());
    }

//    @Test
//    @DisplayName("고용주 - 회원정보 수정 성공")
//    @WithMockUser
//    public void updateCompanyAccountInfoSuccess() throws Exception {
//        // given
//        String updateMyInfoUrl = "/account/companies/{accountId}";
//        UpdateMyProfileReqDto updateMyProfileReqDto = UpdateMyProfileReqDto.builder()
//                .email("test@email.com")
//                .userName("testName")
//                .phoneNumber("010-1111-1111")
//                .gender(GenderType.MALE)
//                .birth(LocalDate.of(2020, 1, 1))
//                .build();
//
//        // when
//        ResultActions result = mockMvc.perform(post(updateMyInfoUrl)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(updateMyProfileReqDto)));
//
//        // then
//        result.andExpectAll(
//                MockMvcResultMatchers.status().isOk(),
//                MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, "application/json"),
//                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
//                MockMvcResultMatchers.jsonPath("$.message").value("수정되었습니다.")
//        ).andDo(print());
//    }

}
