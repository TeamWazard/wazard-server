package shop.wazard.exception;

public class WaitingListNotFoundException extends RuntimeException {
    public WaitingListNotFoundException(String message) {
        super(message);
    }
}
