package src.mua.exception;

/**
 *
 */
public class SyntaxException extends MUAException {

    public SyntaxException(String str) {

        super(str);

    }
    @Override
    public String getMessage() {
        return "Syntax Exception: " + super.getMessage();
    }
}