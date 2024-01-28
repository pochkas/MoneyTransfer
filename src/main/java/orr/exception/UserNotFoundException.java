package orr.exception;

public class UserNotFoundException extends UserFacingException{

    public UserNotFoundException(Long id) {
        super("No user with with id " + id + " found.");
    }
}
