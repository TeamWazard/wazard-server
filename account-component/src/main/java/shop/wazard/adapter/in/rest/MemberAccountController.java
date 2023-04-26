package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.wazard.application.port.in.MemberAccountService;
import shop.wazard.dto.JoinReqDto;
import shop.wazard.dto.JoinResDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/members")
class MemberAccountController {

    private final MemberAccountService memberAccountService;

    @PostMapping("/join")
    public ResponseEntity<JoinResDto> join(@Valid @RequestBody JoinReqDto joinReqDto) {
        JoinResDto joinResDto = memberAccountService.join(joinReqDto);
        return ResponseEntity.ok(joinResDto);
    }

}
