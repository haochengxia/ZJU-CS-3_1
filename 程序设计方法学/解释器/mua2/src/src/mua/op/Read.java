package src.mua.op;


import src.mua.Expr;
import src.mua.dataType.MUAObject;
import src.mua.dataType.Word;
import src.mua.interpreter.Scope;
import src.mua.utils.ArgUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @Method: eval
 * getOpName
 * getArgNum
 **/

public class Read extends Expr {

    static final private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList());

    @Override
    public MUAObject eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        Scanner input = new Scanner(System.in);
        String token = input.next();
        return new Word(token);
    }
    @Override
    public String getOpName() {
        return "read";
    }

    public int getArgNum() {
        return argTypes.size();
    }
}
