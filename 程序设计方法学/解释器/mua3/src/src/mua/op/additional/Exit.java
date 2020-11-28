package src.mua.op.additional;

import src.mua.Expression;
import src.mua.dataType.None;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

/** additional expr
 * @param
 * @return
 */

public class Exit extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList());

    @Override
    public String getOpName() {
        return "exit";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        System.exit(0);
        return new None();
    }

    public int getArgNum() {
        return argTypes.size();
    }

}
