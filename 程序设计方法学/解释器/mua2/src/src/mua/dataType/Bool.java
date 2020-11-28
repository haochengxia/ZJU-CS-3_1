package src.mua.dataType;
/**
 * @Member: value
 * @Method: Bool
 * getTypeString
 * getValue
 * toString
 **/

public class Bool extends MUAObject {

    private boolean value;

    public Bool(boolean b) {
        this.value = b;
    }

    @Override
    public String getTypeString() {
        return "bool";
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value)
            return "true";
        else
            return "false";
    }
}
