package src.mua.op.basic;


import src.mua.Expression;
import src.mua.dataType.Object;
import src.mua.dataType.None;
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

public class Make extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;


    @Override
    public String getOpName() {
        return "make";
    }

    @Override
    public  src.mua.dataType.None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        src.mua.dataType.Word word = ( src.mua.dataType.Word) argList.get(0);
        src.mua.dataType.Object value = argList.get(1);
        nameSpace.addName(word, value);
        return new  src.mua.dataType.None();
    }


    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            src.mua.dataType.Word.class,
            src.mua.dataType.Object.class
    ));

    public int getArgNum() {
        return argTypes.size();
    }
}
