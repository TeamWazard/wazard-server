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
import shop.wazard.dto.UpdateCompanyAccountInfoReqDto;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = WazardApplication.class)
@AutoConfigureMockMvc
class CompanyAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("고용주 - 회원정보 수정 성공")
    @WithMockUser
    public void updateCompanyAccountInfoSuccess() throws Exception {
        // given
        String updateMyInfoUrl = "/account/companies/{accountId}";
        UpdateCompanyAccountInfoReqDto updateCompanyAccountInfoReqDto = new UpdateCompanyAccountInfoReqDto("test@email.com", "홍길동", "FEMALE", LocalDate.of(2000, 1, 1));

        // when
        ResultActions result = mockMvc.perform(post(updateMyInfoUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCompanyAccountInfoReqDto)));

        // then
        result.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, "application/json"),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("$.message").exists()
        ).andDo(print());
    }

}
