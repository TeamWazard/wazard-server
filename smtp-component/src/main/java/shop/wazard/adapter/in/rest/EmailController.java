package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.wazard.application.port.in.EmailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
class EmailController {

    private final EmailService emailService;

//    @PostMapping("/auth")
//    public ResponseEntity<EmailAuthRes> mailAuthentication(@RequestBody EmailAuthReq emailAuthReq) throws MessagingException {
//        return ResponseEntity.ok(
//                EmailAuthRes.builder()
//                        .authenticationCode(emailService.sendEmail(emailAuthReq.getEmail()))
//                        .build()
//        );
//    }

}
