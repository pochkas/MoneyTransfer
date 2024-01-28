package orr.exception;

public class AccountNotFoundException extends UserFacingException {

    public AccountNotFoundException(Long id) {
        super("No account with with id " + id + " found.");
    }
}
