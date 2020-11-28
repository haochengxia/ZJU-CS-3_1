package src.mua.exception;

/**
 *
 */
public class NameException extends MUAException {

    public NameException(String str) {

        super(str);

    }
    @Override
    public String getMessage() {
        return "Name Exception: " + super.getMessage();
    }
}