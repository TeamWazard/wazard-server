package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.wazard.application.port.in.CommonAccountService;
import shop.wazard.dto.LoginReqDto;
import shop.wazard.dto.LoginResDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/common")
class CommonAccountController {
    private final CommonAccountService commonAccountService;

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto loginReqDto) {
        LoginResDto loginResDto = commonAccountService.login(loginReqDto);
        return ResponseEntity.ok(loginResDto);
    }

}
