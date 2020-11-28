package src.mua.utils;


import src.mua.Expression;
import src.mua.dataType.List;
import src.mua.dataType.Object;
import src.mua.interpreter.NameSpace;

import java.util.ArrayList;

import static src.mua.utils.ParserUtil.parseObj;

public class RunTimeUtil {
    public static Object runList(NameSpace nameSpace, List list) throws Exception {
        ArrayList<String> tokens = new ArrayList<>();

        for (Object token : list.getValue()) {
            tokens.add(token.toString());
        }
        // TODO mark sys out
        //for (int i =0;i<tokens.size();i++)
            //System.out.println("in runtime :"+tokens.get(i));
        ArrayList<Object> objList = parseObj(tokens, nameSpace);
        for (Object obj : objList) {
            if (obj instanceof Expression) {
                ((Expression)obj).calculate(nameSpace);
            }
            if (nameSpace.getStopFlag()) {
                return nameSpace.getReturnValue();
            }
        }
        return nameSpace.getReturnValue();
    }
}