package src.mua.dataType;

import src.mua.interpreter.NameSpace;

import java.io.Serializable;

/**
 * @Member: enclosingnameSpace
 * @Method: getTypeName
 * getValue
 * toString
 **/

abstract public class Object implements Serializable {

    public NameSpace enclosingNameSpace = null;

    abstract public String getTypeName();

    abstract public java.lang.Object getValue();

    @Override
    abstract public String toString();

}