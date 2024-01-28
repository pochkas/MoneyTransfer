package orr.exception;

public abstract class UserFacingException extends RuntimeException{
    public UserFacingException(String message) {
        super(message);
    }
}
