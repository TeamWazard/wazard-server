package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import shop.wazard.application.port.in.EmailService;
import shop.wazard.exception.FailCreateEmailForm;
import shop.wazard.exception.FailSendEmail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;  // 타임리프

    @Value("${spring.mail.username}")
    private String senderEmail;
    private String authenticationCode;

    @Override
    public String sendEmail(String email) throws MessagingException {
        MimeMessage emailForm = createEmailForm(email);
        try {
            log.info("================== email = {}, 메일 전송 시작 ====================", email);
            emailSender.send(emailForm);
        } catch (MailException e) {
            throw new FailSendEmail("메일 전송에 실패하였습니다.");
        }
        return authenticationCode;
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {
        String title = "Wazard 회원가입 인증번호";
        MimeMessage message = emailSender.createMimeMessage();
        try {
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(title);
            message.setFrom(senderEmail);
            message.setText(setContext(createCode()), "utf-8", "html");
        } catch (Exception e) {
            throw new FailCreateEmailForm("메일 폼 작성에 실패했습니다.");
        }
        return message;
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("certificationMailForm", context);
    }

    private String createCode() {
        Random random = new Random();
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);  // 0 ~ 2 사이의 난수 발생
            switch (idx) {
                case 0:
                    code.append(Character.toChars(random.nextInt(26) + 97));
                    break;
                case 1:
                    code.append(Character.toChars(random.nextInt(26) + 65));
                    break;
                case 2:
                    code.append(random.nextInt(10));
                    break;
            }
        }
        return authenticationCode = code.toString();
    }

}
