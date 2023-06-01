package shop.wazard.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FailCreateEmailForm extends RuntimeException {

    public FailCreateEmailForm(String message) {
        super(message);
    }

}
