package src.mua.exception;

/**
 *
 */
public class ArithmeticException extends MUAException {

    public ArithmeticException(String str) {

        super(str);

    }

    public String getMessage() {
        return "Arithmetic Exception: " + super.getMessage();
    }
}