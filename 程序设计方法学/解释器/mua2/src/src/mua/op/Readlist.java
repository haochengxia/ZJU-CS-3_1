package src.mua.op;

import src.mua.Expr;
import src.mua.dataType.MUAObject;
import src.mua.interpreter.Scope;
import src.mua.utils.ArgUtil;

import static src.mua.utils.ParserUtil.parseBasicObj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @Method: eval
 * getOpName
 * getArgNum
 **/

public class Readlist extends Expr {

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList());

    @Override
    public String getOpName() {
        return "readlist";
    }

    @Override
    public MUAObject eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        return parseBasicObj("[ " + line + " +]");
    }

    public int getArgNum() {
        return argTypes.size();
    }
}
