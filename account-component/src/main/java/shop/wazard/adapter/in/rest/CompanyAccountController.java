package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.CompanyAccountService;
import shop.wazard.dto.JoinReqDto;
import shop.wazard.dto.JoinResDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/companies")
class CompanyAccountController {

    private final CompanyAccountService companyAccountService;

    // TODO : 회원가입 시 입력받는 값 명세서 기준으로 몇개 추가 필요, JoinReq관련 Converter에도 모든 값 수정 필요, ROLE을 입력하지 않고 회원을 저장하는 경우, Security 관련 로직 수행 중 NullPoinException 발생함
    @PostMapping("/join")
    public ResponseEntity<JoinResDto> join(@Valid @RequestBody JoinReqDto joinReqDto) {
        JoinResDto joinResDto = companyAccountService.join(joinReqDto);
        return ResponseEntity.ok(joinResDto);
    }

    // INFO : 토큰 인증을 필요로 하는 API
    @GetMapping("/test")
    public String test() {
        return "TOKEN TEST SUCCESS";
    }

    //TODO : 로그아웃 -> 어떻게 로그아웃 처리를 해야 할지 고민

}
