package shop.wazard.exception;

public class RegisterPermissionDenied extends RuntimeException {

    public RegisterPermissionDenied(String message) {
        super(message);
    }

}
