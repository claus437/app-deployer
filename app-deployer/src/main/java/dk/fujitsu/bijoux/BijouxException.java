package dk.fujitsu.bijoux;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 31-03-2011
 * Time: 20:38:30
 * To change this template use File | Settings | File Templates.
 */
public class BijouxException extends RuntimeException {
    public BijouxException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BijouxException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BijouxException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BijouxException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
