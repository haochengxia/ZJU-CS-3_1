package src.mua.op.basic;

import src.mua.Expression;
import src.mua.dataType.List;
import src.mua.dataType.Number;
import src.mua.dataType.None;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

import static src.mua.utils.RunTimeUtil.runList;

/**
 * @Method: calculate
 * getOpName
 * getArgNum
 **/

public class Repeat extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            Number.class,
            List.class
    ));

    @Override
    public String getOpName() {
        return "repeat";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Number n = (Number)argList.get(firstArg);
        List l = (List)argList.get(secondArg);
        for (int i = 0; i < n.getValue(); i++) {
            runList(nameSpace, l);
        }
        return new None();
    }

    public int getArgNum() {
        return argTypes.size();

    }
}
