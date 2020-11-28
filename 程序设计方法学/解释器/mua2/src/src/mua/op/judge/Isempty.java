package src.mua.op.judge;

import src.mua.Expr;
import src.mua.dataType.List;
import src.mua.dataType.MUAObject;
import src.mua.dataType.Word;
import src.mua.interpreter.Scope;
import src.mua.utils.ArgUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Method: eval
 * getOpName
 * getArgNum
 **/

public class Isempty extends Expr {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(MUAObject.class));

    @Override
    public String getOpName() {
        return "isempty";
    }

    @Override
    public Word eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        MUAObject obj = argList.get(firstArg);
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

