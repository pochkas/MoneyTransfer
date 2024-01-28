package orr.exception;

public class InsufficientFundsException extends UserFacingException{
    public InsufficientFundsException() {
        super("Insufficient Funds.");
    }
}
