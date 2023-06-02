package shop.wazard.exception;

public class NestedEmailException extends RuntimeException {

    public NestedEmailException(String message) {
        super(message);
    }
}
