package orr.exception;

import orr.dao.impl.MoneyTransferDaoImpl;

public class MoneyTransferException extends RuntimeException{

    public MoneyTransferException(){
        super("Insufficient funds on balance.");
    }
}
