package src.mua.dataType;

public class None extends MUAObject {
    /**
     * @Method: None
     * getTypeString
     * getValue
     * toString
     **/

    public None() { }

    @Override
    public String getTypeString() {
        return "none";
    }

    @Override
    public None getValue() {
        return this;
    }

    @Override
    public String toString() {
        return "None(None Type)";
    }

}