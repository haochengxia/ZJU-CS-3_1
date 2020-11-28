package src.mua.op.func;


import src.mua.Expr;
import src.mua.dataType.None;
import src.mua.interpreter.Scope;
import src.mua.utils.ArgUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Method: eval
 * getOpName
 * getArgNum
 **/


public class Stop extends Expr {

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList());

    @Override
    public String getOpName() {
        return "stop";
    }

    @Override
    public None eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        scope.setStopFlag(true);
        return new None();
    }

    public int getArgNum() {
        return argTypes.size();
    }

}