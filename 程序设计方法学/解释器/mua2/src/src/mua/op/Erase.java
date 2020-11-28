package src.mua.op;


import src.mua.Expr;
import src.mua.dataType.None;
import src.mua.dataType.Word;
import src.mua.interpreter.Scope;
import src.mua.utils.ArgUtil;

import java.util.Arrays;

import java.util.ArrayList;

/**
 * @Method: eval
 * getOpName
 * getArgNum
 **/

public class Erase extends Expr {

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
    public None eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        Word word = (Word) argList.get(firstArg);
        scope.removeName(word);
        return new None();
    }

    @Override
    public int getArgNum() {
        return argTypes.size();
    }
}
