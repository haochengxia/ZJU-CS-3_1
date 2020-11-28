package src.mua.op.wordList;

import src.mua.Expression;
import src.mua.dataType.Number;
import src.mua.dataType.Word;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class List extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    @Override
    public String getOpName() {
        return "list";
    }

    @Override
    public src.mua.dataType.List calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        src.mua.dataType.Object a =  argList.get(firstArg);
        src.mua.dataType.Object b =  argList.get(secondArg);
        ArrayList<src.mua.dataType.Object> list = new ArrayList<>();
        list.add(a);
        list.add(b);

        return new src.mua.dataType.List(list);
    }


    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            src.mua.dataType.Object.class,
            src.mua.dataType.Object.class
    ));

    public int getArgNum() {
        return argTypes.size();
    }
}

