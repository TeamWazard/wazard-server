package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.wazard.adapter.in.rest.request.LoginReq;
import shop.wazard.application.port.in.CommonAccountService;
import shop.wazard.dto.LoginReqDto;
import shop.wazard.dto.LoginResDto;
import shop.wazard.util.response.Message;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/common")
class CommonAccountController {
    private final CommonAccountService commonAccountService;
    private final ControllerConverter controllerConverter;

    @PostMapping("/login")
    public ResponseEntity<Message> login(@Valid @RequestBody LoginReq loginReq) {
        LoginReqDto loginReqDto = controllerConverter.loginReqToLoginReqDto(loginReq);
        LoginResDto loginResDto = commonAccountService.login(loginReqDto);
        return ResponseEntity.ok(
                Message.builder()
                        .httpStatus(HttpStatus.OK)
                        .result(controllerConverter.loginResDtoToLoginRes(loginResDto))
                        .build()
        );
    }
}
