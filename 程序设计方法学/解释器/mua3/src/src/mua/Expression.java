package src.mua;

import src.mua.dataType.Object;
import src.mua.utils.ArgumentUtil;
import src.mua.dataType.*;
import src.mua.interpreter.NameSpace;

import java.util.ArrayList;

/**
 * @Member: argList
 * @Method: calculate
 * getOpName
 * getArgNum
 * setArgList
 * getTypeString
 * getValue
 * toString
 **/


abstract public class Expression extends Object {

    protected ArrayList<Object> argList = new ArrayList<>();

    abstract public String getOpName();
    abstract public int getArgNum();

    @Override
    public String getTypeName() {
        return "expr";
    }

    public Object calculate(NameSpace nameSpace) throws Exception {
        ArgumentUtil.calculateAll(argList, nameSpace);
        return new None();
    }

    public void setArgList(ArrayList<Object> argList) {
        this.argList = argList;
    }


    @Override
    public Expression getValue() {
        return this;
    }

    @Override
    public String toString() {
        String tmp = getOpName();
        for (Object arg : argList) {
            tmp += " " + arg.toString();
        }
        return tmp;
    }
}