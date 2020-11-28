package src.mua.op.operator;

import src.mua.Expr;
import src.mua.dataType.MUAObject;
import src.mua.dataType.Number;
import src.mua.dataType.Word;
import src.mua.interpreter.Scope;
import src.mua.utils.ArgUtil;
import src.mua.utils.TransUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class Eq extends Expr {

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
    public Word eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(getOpName(), argTypes, argList);
        MUAObject x = argList.get(firstArg);
        MUAObject y = argList.get(secondArg);
        if (x instanceof Number && y instanceof Number) {
            Number a = (Number) x;
            Number b = (Number) y;
            return new Word((a.getValue() - b.getValue()) == 0);
        } else {

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
