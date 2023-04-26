package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.wazard.adapter.in.rest.request.EmailAuthReq;
import shop.wazard.adapter.in.rest.response.EmailAuthRes;
import shop.wazard.application.port.in.EmailService;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
class EmailController {

    private final EmailService emailService;

    @PostMapping("/auth")
    public ResponseEntity<EmailAuthRes> mailAuthentication(@RequestBody EmailAuthReq emailAuthReq) throws MessagingException {
        return ResponseEntity.ok(
                EmailAuthRes.builder()
                        .authenticationCode(emailService.sendEmail(emailAuthReq.getEmail()))
                        .build()
        );
    }

}
