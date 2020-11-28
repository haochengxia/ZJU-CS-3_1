package src.mua.dataType;
/**
 * @Member: value
 * @Method: Number
 * getTypeString
 * getValue
 * toString
 **/

public class Number extends MUAObject {

    private double value;

    public Number(double d) {
        this.value = d;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public String getTypeString() {
        return "number";
    }

    @Override
    public Double getValue() {
        return value;
    }


}