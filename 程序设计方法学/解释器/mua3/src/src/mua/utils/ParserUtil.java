package src.mua.utils;

import src.mua.dataType.Number;
import src.mua.dataType.Object;
import src.mua.dataType.Word;
import src.mua.interpreter.NameSpace;
import src.mua.op.additional.Exit;
import src.mua.op.basic.*;
import src.mua.op.operator.*;
import src.mua.op.func.*;
import src.mua.op.judge.*;

import src.mua.Expression;
import src.mua.Function;
import src.mua.op.other.*;
import src.mua.op.value.Int;
import src.mua.op.value.Sqrt;
import src.mua.op.wordList.*;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParserUtil {


//    public static Object evalObj(ArrayList<String> tokens, NameSpace nameSpace) throws Exception {
//        // do evaluation
//        Stack<Object> opStack = new Stack<>();
//        for (int i = tokens.size() - 1; i >= 0; i--) {
//            reduce(tokens.get(i), opStack, nameSpace);
//        }
//
//        return  opStack.pop();
//    }


    private static final HashMap<String, Class> keyword2Class = new HashMap<>();

    static {
        keyword2Class.put("make", Make.class);
        keyword2Class.put("erase", Erase.class);
        keyword2Class.put("print", Print.class);
        keyword2Class.put("readlist", Readlist.class);
        keyword2Class.put(":", Thing.class);
        keyword2Class.put("thing", Thing.class);
        keyword2Class.put("isname", Isname.class);
        keyword2Class.put("read", Read.class);
        keyword2Class.put("add", Add.class);
        keyword2Class.put("sub", Sub.class);
        keyword2Class.put("mul", Mul.class);
        keyword2Class.put("div", Div.class);
        keyword2Class.put("mod", Mod.class);
        keyword2Class.put("eq", Eq.class);
        keyword2Class.put("gt", Gt.class);
        keyword2Class.put("lt", Lt.class);
        keyword2Class.put("and", And.class);
        keyword2Class.put("or", Or.class);
        keyword2Class.put("not", Not.class);
        keyword2Class.put("repeat", Repeat.class);
        keyword2Class.put("output", Output.class);
        keyword2Class.put("stop", Stop.class);
        keyword2Class.put("export", Export.class);
        keyword2Class.put("isnumber", Isnumber.class);
        keyword2Class.put("isword", Isword.class);
        keyword2Class.put("islist", Islist.class);
        keyword2Class.put("isbool", Isbool.class);
        keyword2Class.put("isempty", Isempty.class);
        keyword2Class.put("if", If.class);


        // additional expr
        keyword2Class.put("exit", Exit.class);

        // other op
        keyword2Class.put("wait", Wait.class);
        keyword2Class.put("save", Save.class);
        keyword2Class.put("load", Load.class);
        keyword2Class.put("erall", Erall.class);
        keyword2Class.put("poall", Poall.class);
        keyword2Class.put("run", Run.class);

        // value op
        keyword2Class.put("random",    src.mua.op.value.Random.class);  // avoid conflict
        keyword2Class.put("sqrt",      Sqrt.class);
        keyword2Class.put("int",       Int.class);


        // word list

        keyword2Class.put("butfirst",    Butfirst.class);
        keyword2Class.put("butlast",    Butlast.class);
        keyword2Class.put("first",      First.class);
        keyword2Class.put("last",       Last.class);

        keyword2Class.put("join",      src.mua.op.wordList.Join.class);
        keyword2Class.put("list",       src.mua.op.wordList.List.class);
        keyword2Class.put("sentence",       src.mua.op.wordList.Sentence.class);
        keyword2Class.put("word",       src.mua.op.wordList.Word.class);

        /** TODO waiting for fix in stage 3



         */

    }

    /**
     * @param str
     * @return
     * @throws
     */
    public static ArrayList<String> parseToken(String str) {
        ArrayList<String> tokens = new ArrayList<>();
        // split all the space get empty content
        if (str.trim().equals(""))
            return tokens;
        // split comments
        String[] itemStr = str.trim().split("\\s+");
        ArrayList<String> items = new ArrayList<>(Arrays.asList(itemStr));
        //System.out.println(items.size());
        int count = 0;
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);

            if (item.startsWith("(")){
                // if all in one
                // check items if enough to add
                // 如果括号内有括号或者是函数跟着一个量
//                Map<String,String> map=new HashMap();
//                String protect = "::";
//                int index = 0;
//                map.put(protect+index,)
                    Pattern patternFunc = Pattern.compile("[(a-zA-Z]*");
                    Pattern patternNum = Pattern.compile("[0-9]*");
                Matcher matcherFuncFirst = patternFunc.matcher(item);
                if (matcherFuncFirst.matches()) item = item+"@";
                int opDone = 0;
                while (!item.endsWith(")") && !item.endsWith("]") ) {
                    opDone = 1;
                    Matcher matcherFunc = patternFunc.matcher(items.get(i + 1));
                    item = item + items.get(i + 1);
                    if (matcherFunc.matches()) item = item+"@";
                    items.remove(i+1);
                }

                    while (item.endsWith("]")) {
                        item = item.substring(0,item.length()-1);
                        items.add(i+1,"]");
                }
                InfixUtil infixUtil = new InfixUtil();
                //System.out.println(item);
                //System.out.println(item.replace('@', ' '));
                java.util.List<String> tempE = infixUtil.inTransPre(item.replace('@', ' '));
                //tokens.addAll(tempE);
                // 用这个当前的tempE来换之前的那个元素
//                ArrayList<String> tempItems = new ArrayList<>();
//                for (int j = i+1;j<items.size();j++){
//                    tempItems.add(items.get(j));
//                }
                items.remove(i);
                items.addAll(i,tempE);
                i --;
                continue;
//                for (int j =0;j<tempE.size();j++){
//                    tokens.add(tempE.get(j));
//                }
            }

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
                    // TODO: add parse tool
                    // in name " + - * /"
                    // System.out.println("before" + i);
                    items.set(i, "\"" + item.substring(1));
                    // System.out.println(item.substring(1));
                    // System.out.println("mid" + i);
                    i--;
                    // System.out.println("after" + i);
                    // transfer :name to thing "name

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
                } else if (count == 0) {
                    tokens.add(String.join(" ", temp));
                    temp.clear();
                }
            }
        }

        return tokens;

    }

    public <S,T> Map<T,S> getInverseMap(Map<S,T> map) {
        Map<T,S> inverseMap = new HashMap<T,S>();
        for(Map.Entry<S,T> entry: map.entrySet()) {
            inverseMap.put(entry.getValue(), entry.getKey());
        }
        return inverseMap;
    }
    public static Object parseBasicObj(String str) throws Exception {
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
                throw new Exception();
            }

        }
        // List: contain with [], elements(can be any different types) use Space to separate
        else if (str.startsWith("[") && str.endsWith("]")) {
            // separate all elements and remove []
            ArrayList<String> content = parseToken(str.substring(1, str.length() - 1));
            ArrayList<Object> objList = new ArrayList<>();
            for (String token : content) {
                if (token.startsWith("[") && token.endsWith("]")) {
                    objList.add(parseBasicObj(token));
                } else {
                    objList.add(new Word(token));
                }

            }
            return new src.mua.dataType.List(objList);
        }
//        // solve () eval
//        else if (str.startsWith("(") && str.endsWith(")")){
//
//        }
        else {
            return null;
        }
    }

    public static ArrayList<Object> parseObj(ArrayList<String> tokens, NameSpace nameSpace) throws Exception {
        ArrayList<Object> objlist = new ArrayList<>();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            reduce(tokens.get(i), objlist, nameSpace);
        }
        return objlist;
    }

    /**
     * @param token
     * @param objList
     * @param nameSpace
     * @throws Exception
     */
    public static void reduce(String token, ArrayList<Object> objList, NameSpace nameSpace) throws Exception {
        Object obj = parseBasicObj(token);
        ArrayList<Object> argList = new ArrayList<>();
        Class c = null;
        if (obj != null) {
            objList.add(0, obj);
        } else if (keyword2Class.containsKey(token)) {
            c = keyword2Class.get(token);
            Constructor ctor = c.getConstructor();
            Expression expression = (Expression) ctor.newInstance();
            int argNum = (int) c.getMethod("getArgNum").invoke(expression);

            for (int i = 0; i < argNum; i++) {
                if (!objList.isEmpty()) {
                    argList.add(objList.remove(0));
                }
            }
            expression.setArgList(argList);
            objList.add(0, expression);
        } else {
            c = Function.class;
            Function function = new Function(token, nameSpace);
            int argNum = function.getArgNum();
            for (int i = 0; i < argNum; i++) {
                if (!objList.isEmpty()) {
                    argList.add(objList.remove(0));
                }
            }
            function.setArgList(argList);
            objList.add(0, function);
        }
    }


}