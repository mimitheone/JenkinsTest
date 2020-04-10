package exceptions;

public class DataAccessException extends RuntimeException {

    public DataAccessException(Exception exception) {
        super("A data access framework issue occurred", exception);
    }
}
