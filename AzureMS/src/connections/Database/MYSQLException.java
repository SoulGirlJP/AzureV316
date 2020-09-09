package connections.Database;

public class MYSQLException extends RuntimeException {

    public MYSQLException(String msg) {
        super(msg);
    }

    public MYSQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
