package src.mua.exception;

public class TypeException extends MUAException {

    public TypeException(String str) {
        super(str);
    }

    @Override
    public String getMessage() {
        return "Type Exception: " + super.getMessage();
    }
}