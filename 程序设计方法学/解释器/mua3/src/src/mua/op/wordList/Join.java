package src.mua.op.wordList;

import src.mua.Expression;
import src.mua.dataType.Object;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class Join extends Expression {
    @Override
    public String getOpName() {
        return "join";
    }

    @Override
    public src.mua.dataType.List calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argtypes, argList);
        src.mua.dataType.List a = (src.mua.dataType.List) argList.get(0);
        src.mua.dataType.Object b =  argList.get(1);
        ArrayList <src.mua.dataType.Object> l = (ArrayList<src.mua.dataType.Object>) a.getValue().clone();
        l.add(b);
        return new src.mua.dataType.List(l);

    }

    final static private ArrayList<Class> argtypes = new ArrayList<Class>(Arrays.asList(
            List.class,
            src.mua.dataType.Object.class
    ));
    public int getArgNum() {
        return argtypes.size();
    }
}

