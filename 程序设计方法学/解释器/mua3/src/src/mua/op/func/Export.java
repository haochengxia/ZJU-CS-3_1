package src.mua.op.func;


import src.mua.Expression;
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


public class Export extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(Word.class));

    @Override
    public String getOpName() {
        return "export";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Word word = (Word) argList.get(firstArg);
        nameSpace.getEnclosingnameSpace().addName(word, nameSpace.getName(word));
        return new None();
    }


    public int getArgNum() {
        return argTypes.size();
    }
}
