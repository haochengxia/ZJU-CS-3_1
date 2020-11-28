package src.mua.utils;

import src.mua.Expr;
import src.mua.dataType.Number;
import src.mua.dataType.Bool;
import src.mua.dataType.List;
import src.mua.dataType.MUAObject;
import src.mua.dataType.Word;
import src.mua.exception.ArgException;
import src.mua.exception.TypeException;
import src.mua.interpreter.Scope;

import java.util.ArrayList;
import java.util.HashMap;



public class ArgUtil {
    public static HashMap<Class, String> CLASS_TO_STR = new HashMap<>();
    static {
        CLASS_TO_STR.put(Word.class, "word");
        CLASS_TO_STR.put(Bool.class, "bool");
        CLASS_TO_STR.put(Number.class, "number");
        CLASS_TO_STR.put(List.class, "list");
    }


    public static void argCheck(String name,
                                ArrayList<Class> typelist,
                                ArrayList<MUAObject> arglist) throws ArgException, TypeException {

        // arg count
        if (arglist.size() != typelist.size()) {
            throw new ArgException("arg num not match");
        }


        for (int i = 0; i < arglist.size(); i++) {
            MUAObject obj = typeCast(typelist.get(i), arglist.get(i));
            if (obj != null) {
                arglist.set(i, obj);
            }
            if (!(typelist.get(i).isInstance(arglist.get(i)))) {
                throw new TypeException("arg num not match");
            }
        }
    }

    public static MUAObject typeCast(Class c, MUAObject obj) {
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

    public static void evalAll(ArrayList<MUAObject> arglist, Scope scope) throws Exception {
        for (int i = 0; i < arglist.size(); i++) {

            if (arglist.get(i) instanceof Expr) {
                arglist.set(i, ((Expr) arglist.get(i)).eval(scope));
            }
        }
    }
}