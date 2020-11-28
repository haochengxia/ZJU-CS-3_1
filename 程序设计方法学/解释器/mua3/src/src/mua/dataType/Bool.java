package src.mua.dataType;
/**
 * @Member: value
 * @Method: Bool
 * getTypeName
 * getValue
 * toString
 **/

public class Bool extends Object {

    private boolean value;

    public Bool(boolean b) {
        this.value = b;
    }

    @Override
    public String getTypeName() {
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
