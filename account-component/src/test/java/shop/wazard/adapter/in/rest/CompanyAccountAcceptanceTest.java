package shop.wazard.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.wazard.WazardApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = WazardApplication.class)
@AutoConfigureMockMvc
class CompanyAccountAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("고용주 내  수정 성공 테스트")
    public void companyUpdateMyInfoSuccess() throws Exception {
        // given
        String updateMyInfoUrl = "/account/companies/{accountId}";
        UpdateCompanyAccountInfoReq updateCompanyAccountInfoReq = new UpdateCompanyAccountInfoReq("test@email.com", "홍길동", "FEMALE");

        // when
        ResultActions result = mockMvc.perform(post(updateMyInfoUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateMyInfoReq)));

        // then
        result.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.message").exists()
        ).andDo(print());
    }

}
