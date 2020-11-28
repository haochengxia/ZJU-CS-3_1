package src.mua;

import src.mua.interpreter.*;
import src.mua.dataType.*;
import src.mua.exception.*;
import src.mua.utils.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Member: argList argTypes name argNames body
 * @Method: Func
 * setUp
 * eval
 * getOpName
 * getArgNum
 **/


public class Func extends Expr {
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
    private Scope funcEnclosingScope;

    public Func(String str, Scope scope) throws Exception {
        name = str;
        MUAObject obj = scope.getName(new Word(str));
        setUp(obj);
        funcEnclosingScope = obj.enclosingScope;
    }

    private void setUp(MUAObject obj) throws Exception {
        if (!(obj instanceof List)) {
            throw new SyntaxException("'" + name + "' is not a valid function");
        }
        ArrayList<MUAObject> objList = ((List) obj).getValue();
        if (objList.size() != 2) {
            throw new SyntaxException("'" + name + "' is not a valid function");
        }
        if (!(objList.get(firstObj) instanceof List)) {
            throw new SyntaxException("'" + name + "' is not a valid function");
        }
        if (!(objList.get(secondObj) instanceof List)) {
            throw new SyntaxException("'" + name + "' is not a valid function");
        }
        for (MUAObject arg : ((List) objList.get(firstObj)).getValue()) {
            if (!(arg instanceof Word)) {
                throw new SyntaxException("'" + name + "' is not a valid function");
            }
            argNames.add((Word) arg);
        }
        for (int i = 0; i < argNames.size(); i++) {
            argTypes.add(MUAObject.class);
        }

        funcBody = ((List) objList.get(secondObj));
    }

    @Override
    public MUAObject eval(Scope scope) throws Exception {
        super.eval(scope);
        ArgUtil.argCheck(name, argTypes, argList);
        Scope local = new Scope(name, Scope.Type.FUNCTION, funcEnclosingScope);
        for (int i = 0; i < argNames.size(); i++) {
            local.addName(argNames.get(i), argList.get(i));
        }
        MUAObject ret = RunUtil.runList(local, funcBody);
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