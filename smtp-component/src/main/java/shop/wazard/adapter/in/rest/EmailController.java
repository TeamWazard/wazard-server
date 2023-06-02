package shop.wazard.adapter.in.rest;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.wazard.application.port.in.EmailService;
import shop.wazard.dto.EmailAuthReqDto;
import shop.wazard.dto.EmailAuthResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
class EmailController {

    private final EmailService emailService;

    @PostMapping("/auth")
    public ResponseEntity<EmailAuthResDto> mailAuthentication(
            @Valid @RequestBody EmailAuthReqDto emailAuthReqDto) {
        return ResponseEntity.ok(
                EmailAuthResDto.builder()
                        .authenticationCode(emailService.sendEmail(emailAuthReqDto.getEmail()))
                        .build());
    }
}
