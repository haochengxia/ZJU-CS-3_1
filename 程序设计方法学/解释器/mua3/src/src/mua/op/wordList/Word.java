package src.mua.op.wordList;

import src.mua.Expression;
import src.mua.dataType.Number;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class Word extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    @Override
    public String getOpName() {
        return "word";
    }

    @Override
    public src.mua.dataType.Word calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        src.mua.dataType.Word a =  (src.mua.dataType.Word)argList.get(firstArg);
        src.mua.dataType.Word b =  (src.mua.dataType.Word)argList.get(secondArg);

        return new src.mua.dataType.Word(a.getValue()+b.getValue());
    }


    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            src.mua.dataType.Word.class,
            src.mua.dataType.Word.class
    ));

    public int getArgNum() {
        return argTypes.size();
    }
}

