package src.mua.op.judge;

import src.mua.Expression;
import src.mua.dataType.List;
import src.mua.dataType.Object;
import src.mua.dataType.Word;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Method: calculate
 * getOpName
 * getArgNum
 **/

public class Isempty extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(Object.class));

    @Override
    public String getOpName() {
        return "isempty";
    }

    @Override
    public Word calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Object obj = argList.get(firstArg);
        if (obj instanceof Word) {
            return new Word(((Word)obj).getValue().equals(""));
        }
        else
            return new Word(((List) obj).getValue().size() == 0);
    }

    public int getArgNum() {
        return argTypes.size();
    }
}

