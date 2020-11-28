package src.mua.op.judge;

import src.mua.Expression;
import src.mua.dataType.Bool;
import src.mua.dataType.List;
import src.mua.dataType.None;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;
import src.mua.utils.RunTimeUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Method: calculate
 * getOpName
 * getArgNum
 **/

public class If extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argtypes = new ArrayList<Class>(Arrays.asList(
            Bool.class,
            List.class,
            List.class
    ));

    @Override
    public String getOpName() {
        return "if";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argtypes, argList);
        /** get three para **/
        Bool cond = (Bool) argList.get(firstArg);
        List listA = (List) argList.get(secondArg);
        List listB = (List) argList.get(thirdArg);
        if (cond.getValue()) {
            RunTimeUtil.runList(nameSpace, listA);
        }
        else {
            RunTimeUtil.runList(nameSpace, listB);
        }

        return new None();

    }

    public int getArgNum() {
        return argtypes.size();
    }
}