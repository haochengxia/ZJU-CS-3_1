package src.mua.op.basic;

import src.mua.Expression;
import src.mua.dataType.List;
import src.mua.dataType.Object;
import src.mua.dataType.None;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Method: calculate
 * getOpName
 * getArgNum
 **/

public class Print extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(Object.class));

    @Override
    public String getOpName() {
        return "print";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Object obj = argList.get(0);
        if (obj instanceof List){
            String raw = obj.toString();
            // modified in 2020/1/6
            System.out.println(raw.substring(1,raw.length()-1));
        }
        else{
            System.out.println(obj);
        }
        return new None();

    }

    public int getArgNum() {
        return argTypes.size();
    }
}
