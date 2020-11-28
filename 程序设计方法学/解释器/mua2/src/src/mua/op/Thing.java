package src.mua.op;

import src.mua.Expr;
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

public class Thing extends Expr {

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(Word.class));

    @Override
    public String getOpName() {
        return "thing";
    }

    @Override
    public MUAObject eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        Word word = (Word) argList.get(0);
        return scope.getName(word);

    }

    public int getArgNum() {
        return argTypes.size();
    }
}
