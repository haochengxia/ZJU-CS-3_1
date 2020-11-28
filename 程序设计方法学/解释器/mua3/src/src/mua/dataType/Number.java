package src.mua.dataType;
/**
 * @Member: value
 * @Method: Number
 * getTypeName
 * getValue
 * toString
 **/

public class Number extends Object {

    private double value;

    public Number(double d) {
        this.value = d;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public String getTypeName() {
        return "number";
    }

    @Override
    public Double getValue() {
        return value;
    }


}