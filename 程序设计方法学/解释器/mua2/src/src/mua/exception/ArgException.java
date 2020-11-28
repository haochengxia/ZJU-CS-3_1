package src.mua.exception;

/**
 *
 */

public class ArgException extends MUAException {

    public ArgException(String str) {

        super(str);

    }

    public String getMessage() {
        return "Argument Exception: " + super.getMessage();
    }
}