package src.mua.op.other;

import src.mua.Expression;
import src.mua.dataType.None;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Erall added in 12/27/2019
 */

public class Erall extends Expression{

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    @Override
    public String getOpName() {
        return "erall";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        nameSpace.deleteAllName();
        return new None();
    }


    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
    ));

    public int getArgNum() {
        return argTypes.size();
    }
}
