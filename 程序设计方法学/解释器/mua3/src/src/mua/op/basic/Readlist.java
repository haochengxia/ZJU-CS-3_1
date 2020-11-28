package src.mua.op.basic;

import src.mua.Expression;
import src.mua.dataType.Object;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import static src.mua.utils.ParserUtil.parseBasicObj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @Method: calculate
 * getOpName
 * getArgNum
 **/

public class Readlist extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList());

    @Override
    public String getOpName() {
        return "readlist";
    }

    @Override
    public Object calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        return parseBasicObj("[ " + line + " +]");
    }

    public int getArgNum() {
        return argTypes.size();
    }
}
