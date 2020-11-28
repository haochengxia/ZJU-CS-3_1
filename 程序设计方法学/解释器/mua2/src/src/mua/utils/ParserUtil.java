package src.mua.utils;

import src.mua.dataType.Number;
import src.mua.dataType.MUAObject;
import src.mua.dataType.Word;
import src.mua.dataType.List;
import src.mua.exception.SyntaxException;
import src.mua.interpreter.Scope;
import src.mua.op.*;
import src.mua.op.operator.*;
import src.mua.op.func.*;
import src.mua.op.judge.*;

import src.mua.Expr;
import src.mua.Func;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class ParserUtil {


    /**
     * @param str
     * @return
     * @throws SyntaxException
     */
    public static ArrayList<String> parseToken(String str) throws SyntaxException {
        ArrayList<String> tokens = new ArrayList<>();
        // split all the space get empty content
        if (str.trim().equals(""))
            return tokens;
        // split comments
        String[] itemStr = str.trim().split("\\s+");
        ArrayList<String> items = new ArrayList<>(Arrays.asList(itemStr));
        int count = 0;
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            if (!item.startsWith("[") && !item.endsWith("]") && !item.startsWith(":")) {

                // check whether is in the list

                if (count == 0)
                    tokens.add(item);
                else
                    temp.add(item);
            } else if (item.startsWith(":")) {
                if (count == 0)
                    tokens.add("thing");
                else
                    temp.add("thing");
                if (item.length() > 1) {
                    items.set(i, "\"" + item.substring(1));
                    i--;
                }
            } else {
                String prefix = "[";
                while (item.startsWith(prefix)) {
                    count++;
                    prefix += "[";
                }
                String suffix = "]";
                while (item.endsWith(suffix)) {
                    count--;
                    suffix += "]";
                }
                temp.add(item);
                if (count < 0) {
                    throw new SyntaxException("exist unpaired ']'");
                } else if (count == 0) {
                    tokens.add(String.join(" ", temp));
                    temp.clear();
                }
            }
        }
        if (count != 0) {
            throw new SyntaxException("exist unpaired [");
        }
        return tokens;

    }


    public static MUAObject parseBasicObj(String str) throws Exception {
        // Word: if the start char is ", it is a word
        if (str.startsWith("\"")) {
            return new Word(str.substring(1));
        }
        // Bool: if hold the value false or true, we store them as word as the requirement 1.5
        else if (str.equals("false") || str.equals("true")) {
            return new Word(str);
        }
        // Number: begin with [0~9] or -
        else if (((str.charAt(0) >= '0') && (str.charAt(0) <= '9')) || str.charAt(0) == '-') {
            try {
                // test if match number format
                Number test = new Number(Double.parseDouble(str));
                return new Word(str);
            } catch (NumberFormatException e) {
                throw new SyntaxException("invalid number format: '" + str + "'");
            }

        }
        // List: contain with [], elements(can be any different types) use Space to separate
        else if (str.startsWith("[") && str.endsWith("]")) {
            // separate all elements and remove []
            ArrayList<String> content = parseToken(str.substring(1, str.length() - 1));
            ArrayList<MUAObject> objList = new ArrayList<>();
            for (String token : content) {
                if (token.startsWith("[") && token.endsWith("]")) {
                    objList.add(parseBasicObj(token));
                } else {
                    objList.add(new Word(token));
                }

            }
            return new List(objList);
        } else {
            return null;
        }
    }

    public static ArrayList<MUAObject> parseObj(ArrayList<String> tokens, Scope scope) throws Exception {
        ArrayList<MUAObject> objlist = new ArrayList<>();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            reduce(tokens.get(i), objlist, scope);
        }
        return objlist;
    }

    /**
     * @param token
     * @param objList
     * @param scope
     * @throws Exception
     */
    public static void reduce(String token, ArrayList<MUAObject> objList, Scope scope) throws Exception {
        MUAObject obj = parseBasicObj(token);
        ArrayList<MUAObject> argList = new ArrayList<>();
        Class c = null;
        if (obj != null) {
            objList.add(0, obj);
        } else if (keywordToClass.containsKey(token)) {
            c = keywordToClass.get(token);
            Constructor ctor = c.getConstructor();
            Expr expr = (Expr) ctor.newInstance();
            int argNum = (int) c.getMethod("getArgNum").invoke(expr);

            for (int i = 0; i < argNum; i++) {
                if (!objList.isEmpty()) {
                    argList.add(objList.remove(0));
                }
            }
            expr.setArgList(argList);
            objList.add(0, expr);
        } else {
            c = Func.class;
            Func func = new Func(token, scope);
            int argNum = func.getArgNum();
            for (int i = 0; i < argNum; i++) {
                if (!objList.isEmpty()) {
                    argList.add(objList.remove(0));
                }
            }
            func.setArgList(argList);
            objList.add(0, func);
        }
    }




    private static final HashMap<String, Class> keywordToClass = new HashMap<>();

    static {
        keywordToClass.put("make", Make.class);
        keywordToClass.put("erase", Erase.class);
        keywordToClass.put("print", Print.class);
        keywordToClass.put("readlist", Readlist.class);
        keywordToClass.put(":", Thing.class);
        keywordToClass.put("thing", Thing.class);
        keywordToClass.put("isname", Isname.class);
        keywordToClass.put("read", Read.class);
        keywordToClass.put("add", Add.class);
        keywordToClass.put("sub", Sub.class);
        keywordToClass.put("mul", Mul.class);
        keywordToClass.put("div", Div.class);
        keywordToClass.put("mod", Mod.class);
        keywordToClass.put("eq", Eq.class);
        keywordToClass.put("gt", Gt.class);
        keywordToClass.put("lt", Lt.class);
        keywordToClass.put("and", And.class);
        keywordToClass.put("or", Or.class);
        keywordToClass.put("not", Not.class);
        keywordToClass.put("repeat", Repeat.class);
        keywordToClass.put("output", Output.class);
        keywordToClass.put("stop", Stop.class);
        keywordToClass.put("export", Export.class);
        keywordToClass.put("isnumber", Isnumber.class);
        keywordToClass.put("isword", Isword.class);
        keywordToClass.put("islist", Islist.class);
        keywordToClass.put("isbool", Isbool.class);
        keywordToClass.put("isempty", Isempty.class);
        keywordToClass.put("if", If.class);


        // additional expr
        keywordToClass.put("exit", Exit.class);

        /** TODO waiting for fix in stage 3
        keywordToClass.put("random",    Random.class);
        keywordToClass.put("sqrt",      Sqrt.class);
        keywordToClass.put("int",       Int.class);

        keywordToClass.put("wait",      Wait.class);
        keywordToClass.put("save",      Save.class);
        keywordToClass.put("load",      Load.class);
        keywordToClass.put("erall",     Erall.class);
        keywordToClass.put("poall",     Poall.class);
         */

    }
}