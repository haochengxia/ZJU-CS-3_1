package src.mua.interpreter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import src.mua.dataType.*;
import src.mua.dataType.Object;

public class NameSpace implements Serializable {

    /**
     * Description: As we add func, we need GLOBAL nameSpace and FUNCTION nameSpace
     */


    public enum Type {
        GLOBAL,
        LOCAL
    }

    private String nameSpaceName = "global";
    private Type nameSpaceType = Type.GLOBAL;
    private NameSpace enclosingNameSpace = null;
    private Object returnValue = new None();

    private boolean stopFlag = false;
    public HashMap<String, Object> nameSpace = new HashMap<>();

    public NameSpace() {
        this("global", Type.GLOBAL, null);
    }

    /**
     *
     * @param name
     * @param type
     * @param enclosing
     */
    // create a function nameSpace
    public NameSpace(String name, Type type, NameSpace enclosing) {
        this.nameSpaceName = name;
        this.nameSpaceType = type;
        this.enclosingNameSpace = enclosing;
    }


    /**
     *
     * @return
     */
    public NameSpace getEnclosingnameSpace() {
        return enclosingNameSpace;
    }

    /**
     *
     * @param name
     * @param value
     */
    public void addName(Word name, Object value) {
        nameSpace.put(name.getValue(), value);
        if (value.enclosingNameSpace == null) {
            value.enclosingNameSpace = this;
        }
    }

    public Set<String> getAllName(){
        return nameSpace.keySet();
    }

    /**
     * @param
     */
    public void deleteAllName(){
        Vector<String> name = new Vector<String>();
        for (String str : getAllName())
        name.add(str);

        for (int i =0;i<name.size();i++){
            nameSpace.remove(name.get(i));
        }
    }
    public void addAllName(NameSpace newNameSpace){
        for (Object obj : newNameSpace.nameSpace.values()) {
            obj.enclosingNameSpace = this;
        }
        //System.out.println("before put all"+newNameSpace.nameSpace.toString());
        for (String str : getAllName())
            System.out.println(str);
        nameSpace.putAll(newNameSpace.nameSpace);
        // we need to update the interpreter global
        //for (String str : getAllName())
        //System.out.println("After put all"+nameSpace.toString());
        for (String str : getAllName())
            System.out.println(str);
    }




    /**
     *
     * @param name
     * @return
     * @throws
     */
    public Object getName(Word name)  {
        Object ret = nameSpace.get(name.toString());
        if (ret == null) {
            if (enclosingNameSpace == null) {
            }
            else
                return enclosingNameSpace.getName(name);
        }
        return ret;
    }

    /**
     *
     * @param name
     * @throws
     */
    public void removeName(Word name)  {
        Object succeed = nameSpace.remove(name.getValue());
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean hasName(Word name) {
        return nameSpace.containsKey(name.getValue());
    }

    /**
     *
     * @param obj
     */
    public void setReturnValue(Object obj) {
        returnValue = obj;
    }

    /**
     *
     * @return
     */
    public Object getReturnValue() {
        return returnValue;
    }

    public String getNameSpaceName() {
        return nameSpaceName;
    }

    public Type getNameSpaceType() {
        return nameSpaceType;
    }

    public boolean getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }
}