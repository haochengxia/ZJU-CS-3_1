package src.mua.op.operator;

import src.mua.Expression;
import src.mua.dataType.*;
import src.mua.dataType.Number;
import src.mua.dataType.Object;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;
import src.mua.utils.TransUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class Eq extends Expression {

    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;
    public static final double INFINITY = 1E100;
    @Override
    public String getOpName() {
        return "eq";
    }

    @Override
    public Word calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);
        Object x = argList.get(firstArg);
        Object y = argList.get(secondArg);
        if (x instanceof Number && y instanceof Number) {
            Number a = (Number) x;
            Number b = (Number) y;
            return new Word((a.getValue() - b.getValue()) == 0);
        } else if ((x instanceof List && !(y instanceof List)) ||(y instanceof List && !(x instanceof List))){
            Boolean flag = false;
            return new Word(flag);
        }
        else {

            Word a = (Word) x;
            Word b = (Word) y;
            String p1 = a.toString();
            String p2 = b.toString();
            //in this case if the string is match to the number format we need to translate auto
            if (TransUtil.translate(p1)!=INFINITY && TransUtil.translate(p2)!=INFINITY){
                return new Word (TransUtil.translate(p1) ==  TransUtil.translate(p2));
            }

            return new Word(a.toString().compareTo(b.toString()) == 0);
        }
    }


    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            Word.class,
            Word.class
    ));

    public int getArgNum() {
        return argTypes.size();
    }
}
