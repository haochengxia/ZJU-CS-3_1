package src.mua.op.wordList;


import src.mua.Expression;
import src.mua.dataType.List;
import src.mua.dataType.Number;
import src.mua.dataType.Word;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class Sentence extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    @Override
    public String getOpName() {
        return "sentence";
    }

    @Override
    public src.mua.dataType.List calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        ArrayList<src.mua.dataType.Object> l = new ArrayList<>();

        for (int i = 0; i < getArgNum(); i++) {
            src.mua.dataType.Object obj = argList.get(i);
            if (obj instanceof src.mua.dataType.List) {
                //System.out.println("in sentence size"+((List) obj).getSize());
                for (int j =0;j<((List) obj).getSize();j++)
                {
                   // System.out.println("in sentence "+((List) obj).getValue(j).toString());
                    l.add(((List) obj).getValue(j));
                }

            }
            else {
                l.add(obj);
            }

        }

        return new src.mua.dataType.List(l);
    }


    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            src.mua.dataType.Object.class,
            src.mua.dataType.Object.class
    ));

    public int getArgNum() {
        return argTypes.size();
    }
}

