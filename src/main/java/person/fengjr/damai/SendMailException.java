package person.fengjr.damai;

/**
 * @author fengjr
 * @date 2019/8/30.
 */
public class SendMailException extends RuntimeException {
    public SendMailException() {
        super();
    }

    public SendMailException(String message) {
        super(message);
    }

    public SendMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendMailException(Throwable cause) {
        super(cause);
    }

    protected SendMailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
