package shop.wazard.application.port.in;

import javax.mail.MessagingException;

public interface EmailService {

    String sendEmail(String email) throws MessagingException;

}
