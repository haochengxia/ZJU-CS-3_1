package src.mua.interpreter;
import src.mua.exception.*;
import java.util.HashMap;
import src.mua.dataType.*;

public class Scope {

    /**
     * Description: As we add func, we need GLOBAL scope and FUNCTION scope
     */


    public enum Type {
        GLOBAL,
        FUNCTION
    }

    private String scopeName = "global";
    private Type scopeType = Type.GLOBAL;
    private Scope enclosingScope = null;
    private MUAObject returnValue = new None();

    private boolean stopFlag = false;
    private HashMap<String, MUAObject> scope = new HashMap<>();

    public Scope() {
        this("global", Type.GLOBAL, null);
    }

    /**
     *
     * @param name
     * @param type
     * @param enclosing
     */
    // create a function scope
    public Scope(String name, Type type, Scope enclosing) {
        this.scopeName = name;
        this.scopeType = type;
        this.enclosingScope = enclosing;
    }


    /**
     *
     * @return
     */
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    /**
     *
     * @param name
     * @param value
     */
    public void addName(Word name, MUAObject value) {
        scope.put(name.getValue(), value);
        if (value.enclosingScope == null) {
            value.enclosingScope = this;
        }
    }

    /**
     *
     * @param name
     * @return
     * @throws NameException
     */
    public MUAObject getName(Word name) throws NameException {
        MUAObject ret = scope.get(name.toString());
        if (ret == null) {
            if (enclosingScope == null) {
                throw new NameException("name '" + name.getValue() + "' not found");
            }
            else
                return enclosingScope.getName(name);
        }
        return ret;
    }

    /**
     *
     * @param name
     * @throws NameException
     */
    public void removeName(Word name) throws NameException {
        MUAObject succeed = scope.remove(name.getValue());
        if (succeed == null) {
            throw new NameException("name '" + name.getValue() + "' not found");
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean hasName(Word name) {
        return scope.containsKey(name.getValue());
    }

    /**
     *
     * @param obj
     */
    public void setReturnValue(MUAObject obj) {
        returnValue = obj;
    }

    /**
     *
     * @return
     */
    public MUAObject getReturnValue() {
        return returnValue;
    }

    public String getScopeName() {
        return scopeName;
    }

    public Type getScopeType() {
        return scopeType;
    }

    public boolean getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }
}