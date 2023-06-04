package shop.wazard.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FailSendEmail extends RuntimeException {

    public FailSendEmail(String message) {
        super(message);
    }
}
