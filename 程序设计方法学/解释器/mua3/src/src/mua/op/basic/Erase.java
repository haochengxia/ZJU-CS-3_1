package src.mua.op.basic;


import src.mua.Expression;
import src.mua.dataType.None;
import src.mua.dataType.Word;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.Arrays;

import java.util.ArrayList;

/**
 * @Method: calculate
 * getOpName
 * getArgNum
 **/

public class Erase extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(Word.class));

    @Override
    public String getOpName() {
        return "erase";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Word word = (Word) argList.get(firstArg);
        nameSpace.removeName(word);
        return new None();
    }

    @Override
    public int getArgNum() {
        return argTypes.size();
    }
}
