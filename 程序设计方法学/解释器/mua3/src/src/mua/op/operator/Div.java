package src.mua.op.operator;

import src.mua.Expression;
import src.mua.dataType.*;
import src.mua.dataType.Number;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class Div extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    @Override
    public String getOpName() {
        return "div";
    }

    @Override
    public Word calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Number a = (Number) argList.get(firstArg);
        Number b = (Number) argList.get(secondArg);
        return new Word(a.getValue() / b.getValue());
    }



    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            Number.class,
            Number.class
    ));
    public int getArgNum() {
        return argTypes.size();
    }
}
