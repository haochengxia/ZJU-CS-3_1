package src.mua.op.basic;


import src.mua.Expression;
import src.mua.dataType.Object;
import src.mua.dataType.Word;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @Method: calculate
 * getOpName
 * getArgNum
 **/

public class Read extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    static final private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList());

    @Override
    public Object calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
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
