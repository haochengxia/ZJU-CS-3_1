package src.mua;

import src.mua.dataType.Object;
import src.mua.interpreter.*;
import src.mua.dataType.*;
import src.mua.utils.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Member: argList argTypes name argNames body
 * @Method: Func
 * setUp
 * calculate
 * getOpName
 * getArgNum
 **/


public class Function extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    final private ArrayList<Class> argTypes = new ArrayList<>(Arrays.asList());

    private String name;
    private ArrayList<Word> argNames = new ArrayList<>();
    private List funcBody;
    private NameSpace funcEnclosingNameSpace;

    public Function(String str, NameSpace nameSpace) throws Exception {
        name = str;
        Object obj = nameSpace.getName(new Word(str));
        setUp(obj);
        funcEnclosingNameSpace = obj.enclosingNameSpace;
    }

    private void setUp(Object obj) throws Exception {

        //System.out.println("in setup"+obj.toString());

        ArrayList<Object> objList = ((List) obj).getValue();

        // TODO: patch in not a good plan, fix it
        //System.out.println("in setup ele:"+ objList.get(0)); System.out.println("in setup ele:"+ objList.get(0)+" sss" +objList.get(1));
        //专门针对[] []打一个patch
        ArrayList<Object> emptyObjList = new ArrayList<Object>();
        List emptyList = new List(emptyObjList);
        None none = new None();
        if (objList.get(0).toString().equals("[][]")) {
            objList.set(0,emptyList);
            objList.add(emptyList);
        }



        for (Object arg : ((List) objList.get(firstObj)).getValue()) {
            argNames.add((Word) arg);
        }
        for (int i = 0; i < argNames.size(); i++) {
            argTypes.add(Object.class);
        }
        // 如果函数体为空的
       if (objList.size() > 1)
        funcBody = ((List) objList.get(secondObj));
        else funcBody = emptyList;
    }

    @Override
    public Object calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(name, argTypes, argList);
        NameSpace local = new NameSpace(name, NameSpace.Type.LOCAL, funcEnclosingNameSpace);
        //System.out.println("arg num = "+ argNames.size() + "argName"+argNames.toString());
        for (int i = 0; i < argNames.size(); i++) {
            // System.out.println("arg num = "+ argNames.size() + "argName"+argNames.toString());
            local.addName(argNames.get(i), argList.get(i));
        }
        Object ret = RunTimeUtil.runList(local, funcBody);
        return ret;
    }


    @Override
    public String getOpName() {
        return name;
    }


    @Override
    public int getArgNum() {
        return argTypes.size();
    }

}