package src.mua.op;

import src.mua.Expr;
import src.mua.dataType.None;
import src.mua.dataType.Word;
import src.mua.interpreter.Scope;
import src.mua.utils.ArgUtil;

import java.util.ArrayList;
import java.util.Arrays;

/** additional expr
 * @param
 * @return
 */

public class Exit extends Expr {

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList());

    @Override
    public String getOpName() {
        return "exit";
    }

    @Override
    public None eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        System.exit(0);
        return new None();
    }

    public int getArgNum() {
        return argTypes.size();
    }

}
