package src.mua.dataType;

import src.mua.interpreter.Scope;
/**
 * @Member: enclosingScope
 * @Method: getTypeString
 * getValue
 * toString
 **/

abstract public class MUAObject {

    public Scope enclosingScope = null;

    abstract public String getTypeString();

    abstract public Object getValue();

    @Override
    abstract public String toString();

}