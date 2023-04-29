package shop.wazard.adapter.in.rest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

//@SpringBootTest(classes = WazardApplication.class)
@AutoConfigureMockMvc
class AccountControllerTestJpa {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
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
