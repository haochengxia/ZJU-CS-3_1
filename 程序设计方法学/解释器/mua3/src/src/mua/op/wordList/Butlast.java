package src.mua.op.wordList;

import src.mua.Expression;
import src.mua.dataType.Object;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class Butlast extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    @Override
    public String getOpName() {
        return "butlast";
    }

    @Override
    public Object calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Object obj = (Object) argList.get(firstArg);
        if (obj instanceof src.mua.dataType.List) {
            src.mua.dataType.List l = (src.mua.dataType.List)obj;
            ArrayList<src.mua.dataType.Object> list = (ArrayList<src.mua.dataType.Object>) l.getValue().clone();
            if (list.size() == 0) {
                return new src.mua.dataType.List(list);
            }
            list.remove(list.size() - 1);

            return new src.mua.dataType.List(list);
        }
        else {
            src.mua.dataType.Word w = (src.mua.dataType.Word)obj;
            String str = w.getValue();
            if (w.getValue().length() == 0) {
                return new src.mua.dataType.Word(str);
            }
            return new src.mua.dataType.Word(str.substring(0, str.length() - 1));
        }

    }

    // no parameters
    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            src.mua.dataType.Object.class
    ));

    public int getArgNum() {
        return argTypes.size();
    }
}
