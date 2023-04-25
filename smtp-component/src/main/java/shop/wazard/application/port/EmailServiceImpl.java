package shop.wazard.application.port;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import shop.wazard.application.port.in.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;  // 타임리프

    private String authenticationCode;

    @Override
    public String sendEmail(String email) throws MessagingException {
        MimeMessage emailForm = createEmailForm(email);
        emailSender.send(emailForm);
        return authenticationCode;
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {
        String senderEmail = "simhani1@naver.com";
        String title = "Wazard 회원가입 인증번호";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(title);
        message.setFrom(senderEmail);
        message.setText(setContext(createCode()), "utf-8", "html");
        return message;
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mailForm", context);
    }

    private String createCode() {
        Random random = new Random();
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);  // 0 ~ 2 사이의 난수 발생
            switch (idx) {
                case 0:
                    code.append(Character.forDigit(random.nextInt(26) + 97, 10));
                    break;
                case 1:
                    code.append(Character.forDigit(random.nextInt(26) + 65, 10));
                    break;
                case 2:
                    code.append(random.nextInt(10));
                    break;
            }
        }
        return authenticationCode = code.toString();
    }

}
