package shop.wazard.exception;

import lombok.NoArgsConstructor;

import javax.mail.MessagingException;

@NoArgsConstructor
public class FailSendEmail extends MessagingException {

    public FailSendEmail(String message) {
        super(message);
    }

}
