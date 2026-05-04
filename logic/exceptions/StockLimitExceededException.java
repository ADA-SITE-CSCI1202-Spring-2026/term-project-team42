package logic.exceptions;

public class StockLimitExceededException extends Exception {
    public StockLimitExceededException(String message) {
        super(message);
    }
}