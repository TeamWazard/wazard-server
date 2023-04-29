package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.AccountService;
import shop.wazard.dto.*;
import shop.wazard.util.aop.Certification;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
class AccountController {

    private final AccountService accountService;

    // TODO : 회원가입 시 입력받는 값 명세서 기준으로 몇개 추가 필요, JoinReq관련 Converter에도 모든 값 수정 필요, ROLE을 입력하지 않고 회원을 저장하는 경우, Security 관련 로직 수행 중 NullPoinException 발생함
    @PostMapping("/join")
    public ResponseEntity<JoinResDto> join(@Valid @RequestBody JoinReqDto joinReqDto) {
        JoinResDto joinResDto = accountService.join(joinReqDto);
        return ResponseEntity.ok(joinResDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto loginReqDto) {
        LoginResDto loginResDto = accountService.login(loginReqDto);
        return ResponseEntity.ok(loginResDto);
    }


    @PostMapping("/{accountId}")
    public ResponseEntity<UpdateMyProfileResDto> updateAccountInfo(@PathVariable Long accountId, @Valid @RequestBody UpdateMyProfileReqDto updateMyProfileReqDto) {
        UpdateMyProfileResDto updateMyProfileResDto = accountService.updateMyProfile(updateMyProfileReqDto);
        return ResponseEntity.ok(updateMyProfileResDto);
    }

    /*
    * 토큰 & accountId로 본인 인증 aop 테스트
    * */
    @Certification
    @GetMapping("/test/{accountId}}")
    public String test(@PathVariable Long accountId) {
        return "TEST SUCCESS";
    }

    //TODO : 로그아웃 -> 어떻게 로그아웃 처리를 해야 할지 고민

}
