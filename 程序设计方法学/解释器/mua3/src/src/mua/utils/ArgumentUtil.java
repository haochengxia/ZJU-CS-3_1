package src.mua.utils;

import src.mua.Expression;
import src.mua.dataType.Number;
import src.mua.dataType.Bool;
import src.mua.dataType.Object;
import src.mua.dataType.Word;
import src.mua.interpreter.NameSpace;

import java.util.ArrayList;


public class ArgumentUtil {


    public static void argCheck(String name,
                                ArrayList<Class> typeList,
                                ArrayList<Object> argList) {

        for (int i = 0; i < argList.size(); i++) {
            Object obj = typeCast(typeList.get(i), argList.get(i));
            if (obj != null) {
                argList.set(i, obj);
            }
        }
    }

    public static Object typeCast(Class c, Object obj) {
        if (obj instanceof Word) {
            if (c == Number.class) {
                return ((Word) obj).toNumber();
            }
            else if (c == Bool.class) {
                return ((Word) obj).toBool();
            }
        }

        if (c == Word.class) {
            if (obj instanceof Number) {
                return new Word(obj.toString());
            }
            else if (obj instanceof Bool) {
                return new Word(obj.toString());
            }
        }
        return null;
    }

    public static void calculateAll(ArrayList<Object> argList, NameSpace nameSpace) throws Exception {
        for (int i = 0; i < argList.size(); i++) {

            if (argList.get(i) instanceof Expression) {
                argList.set(i, ((Expression) argList.get(i)).calculate(nameSpace));
            }
        }
    }
}