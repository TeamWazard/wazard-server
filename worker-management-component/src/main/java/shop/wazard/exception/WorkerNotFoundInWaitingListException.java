package shop.wazard.exception;

public class WorkerNotFoundInWaitingListException extends RuntimeException {

    public WorkerNotFoundInWaitingListException(String message) {
        super(message);
    }
}
