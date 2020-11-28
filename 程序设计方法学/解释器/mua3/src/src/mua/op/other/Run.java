package src.mua.op.other;

import src.mua.Expression;
import src.mua.dataType.List;
import src.mua.dataType.None;
import src.mua.dataType.Object;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

import static src.mua.utils.RunTimeUtil.runList;

/**
 * run added in 12/27/2019
 */

public class Run extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    @Override
    public String getOpName() {
        return "run";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        // get list
        List list = (List) argList.get(firstArg);
        // run　list
        runList(nameSpace, list);
        return new None();
    }

    // no parameters
    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            List.class
    ));

    public int getArgNum() {
        return argTypes.size();
    }

}
