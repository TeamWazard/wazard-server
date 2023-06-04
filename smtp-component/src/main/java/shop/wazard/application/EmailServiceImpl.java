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
import shop.wazard.dto.InviteWorkerReqDto;
import shop.wazard.exception.FailCreateEmailForm;
import shop.wazard.exception.FailSendEmail;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine; // 타임리프

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public String sendEmail(String email) {
        MimeMessage emailForm = createEmailForm(email);
        try {
            log.info("================== email = {}, 메일 전송 시작 ====================", email);
            emailSender.send(emailForm);
        } catch (MailException e) {
            log.info("메일 전송에 실패, message = {}", e.getMessage(), e);
            throw new FailSendEmail("메일 전송에 실패하였습니다.");
        }
        return createCode();
    }

    @Override
    public String sendInviteCode(InviteWorkerReqDto inviteWorkerReqDto) {
        MimeMessage emailForm = createInviteCodeForm(inviteWorkerReqDto);
        try {
            log.info("================== email = {}, 메일 전송 시작 ====================", inviteWorkerReqDto.getEmail());
            emailSender.send(emailForm);
        } catch (MailException e) {
            log.info("메일 전송에 실패, message = {}", e.getMessage(), e);
            throw new FailSendEmail("메일 전송에 실패하였습니다.");
        }
        return createCode();
    }

    private MimeMessage createInviteCodeForm(InviteWorkerReqDto inviteWorkerReqDto) {
        String title = "Wazard 업장 초대 인증번호";
        return getMimeMessage(inviteWorkerReqDto, title);
    }

    private MimeMessage createEmailForm(String email) {
        String title = "Wazard 회원가입 인증번호";
        return getMimeMessage(email, title);
    }

    private MimeMessage getMimeMessage(InviteWorkerReqDto inviteWorkerReqDto, String title) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            message.addRecipients(MimeMessage.RecipientType.TO, inviteWorkerReqDto.getEmail());
            message.setSubject(title);
            message.setFrom(senderEmail);
            message.setText(setContextForInviteWorker(inviteWorkerReqDto, createCode()), "utf-8", "html");
        } catch (Exception e) {
            log.info("메일 폼 작성에 실패, message = {}", e.getMessage(), e);
            throw new FailCreateEmailForm("메일 폼 작성에 실패했습니다.");
        }
        return message;
    }

    private MimeMessage getMimeMessage(String email, String title) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(title);
            message.setFrom(senderEmail);
            message.setText(setContextForCertification(createCode()), "utf-8", "html");
        } catch (Exception e) {
            log.info("메일 폼 작성에 실패, message = {}", e.getMessage(), e);
            throw new FailCreateEmailForm("메일 폼 작성에 실패했습니다.");
        }
        return message;
    }

    private String setContextForInviteWorker(InviteWorkerReqDto inviteWorkerReqDto, String code) {
        Context context = new Context();
        context.setVariable("companyName", inviteWorkerReqDto.getCompanyName());
        context.setVariable("code", code);
        return templateEngine.process("inviteMailForm", context);
    }

    private String setContextForCertification(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("certificationMailForm", context);
    }

    private String createCode() {
        Random random = new Random();
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3); // 0 ~ 2 사이의 난수 발생
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
        return code.toString();
    }
}
