package orr.exception;

import orr.dao.impl.MoneyTransferDaoImpl;

public class MoneyTransferException extends UserFacingException{

    public MoneyTransferException(Long id){
        super("No moneyTransfer with id " + id + " found.");
    }
}
