package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.adapter.in.rest.request.JoinReq;
import shop.wazard.adapter.in.rest.request.LoginReq;
import shop.wazard.application.port.in.CompanyAccountService;
import shop.wazard.dto.JoinReqDto;
import shop.wazard.dto.JoinResDto;
import shop.wazard.dto.LoginReqDto;
import shop.wazard.dto.LoginResDto;
import shop.wazard.util.response.Message;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
class CompanyAccountController {

    private final CompanyAccountService companyAccountService;
    private final ControllerConverter controllerConverter;

    // TODO : 회원가입 시 입력받는 값 명세서 기준으로 몇개 추가 필요, JoinReq관련 Converter에도 모든 값 수정 필요, ROLE을 입력하지 않고 회원을 저장하는 경우, Security 관련 로직 수행 중 NullPoinException 발생함
    @PostMapping("/join")
    public ResponseEntity<Message> join(@Valid @RequestBody JoinReq joinreq) {
        JoinReqDto joinReqDto = controllerConverter.joinReqToJoinReqDto(joinreq);
        JoinResDto joinResDto = companyAccountService.join(joinReqDto);
        return ResponseEntity.ok(
                Message.builder()
                        .httpStatus(HttpStatus.OK)
                        .result(controllerConverter.joinResDtoToJoinRes(joinResDto))
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Message> login(@Valid @RequestBody LoginReq loginReq) {
        LoginReqDto loginReqDto = controllerConverter.loginReqToLoginReqDto(loginReq);
        LoginResDto loginResDto = companyAccountService.login(loginReqDto);
        return ResponseEntity.ok(
                Message.builder()
                        .httpStatus(HttpStatus.OK)
                        .result(controllerConverter.loginResDtoToLoginRes(loginResDto))
                        .build()
        );
    }

    // INFO : 토큰 인증을 필요로 하는 API
    @GetMapping("/test")
    public String test() {
        return "TOKEN TEST SUCCESS";
    }

    //TODO : 로그아웃 -> 어떻게 로그아웃 처리를 해야 할지 고민

}
