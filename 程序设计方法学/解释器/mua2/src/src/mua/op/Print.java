package src.mua.op;

import src.mua.Expr;
import src.mua.dataType.MUAObject;
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

public class Print extends Expr {

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(MUAObject.class));

    @Override
    public String getOpName() {
        return "print";
    }

    @Override
    public None eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        MUAObject obj = argList.get(0);
        System.out.println(obj);
        return new None();

    }

    public int getArgNum() {
        return argTypes.size();
    }
}
