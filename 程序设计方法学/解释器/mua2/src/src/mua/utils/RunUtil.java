package src.mua.utils;


import src.mua.Expr;
import src.mua.dataType.List;
import src.mua.dataType.MUAObject;
import src.mua.interpreter.Scope;

import java.util.ArrayList;

import static src.mua.utils.ParserUtil.parseObj;

public class RunUtil {
    public static MUAObject runList(Scope scope, List list) throws Exception {
        ArrayList<String> tokens = new ArrayList<>();
        for (MUAObject token : list.getValue()) {
            tokens.add(token.toString());
        }
        ArrayList<MUAObject> objList = parseObj(tokens, scope);
        for (MUAObject obj : objList) {
            if (obj instanceof Expr) {
                ((Expr)obj).eval(scope);
            }
            if (scope.getStopFlag()) {
                return scope.getReturnValue();
            }
        }
        return scope.getReturnValue();
    }
}