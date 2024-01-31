package orr.exception;

public class MoneyTransferSameAccountException extends UserFacingException{
    public MoneyTransferSameAccountException() {
        super("You can not transfer funds to the same account.");
    }
}
