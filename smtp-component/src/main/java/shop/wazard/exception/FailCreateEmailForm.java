package shop.wazard.exception;


import lombok.NoArgsConstructor;

import javax.mail.MessagingException;

@NoArgsConstructor
public class FailCreateEmailForm extends MessagingException {

    public FailCreateEmailForm(String message) {
        super(message);
    }

}
