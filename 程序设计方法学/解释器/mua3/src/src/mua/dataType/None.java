package src.mua.dataType;

import src.mua.interpreter.NameSpace;

import java.util.HashMap;

public class None extends Object {
    /**
     * @Method: None
     * getTypeName
     * getValue
     * toString
     **/
    public HashMap<String, Object> noneNameSpace = new HashMap<>();
    public String test;
    public String ifNameAdded = "false";
    public None() { }
//    public None(HashMap<String, Object> retNameSpace){
//        System.out.println("when we create the None with namespace para"+ retNameSpace.toString());
//        this.noneNameSpace.putAll(retNameSpace);
//        System.out.println("when we create the None with namespace"+ noneNameSpace.toString());
//        ifNameAdded = "true";
//    }

    public None(String str){
        test = str;
        ifNameAdded = "true";
    }

    @Override
    public String getTypeName() {
        return "none";
    }

    @Override
    public None getValue() {
        return this;
    }

    @Override
    public String toString() {
        return "None(None Type)";
    }


    public String ifNameAdded(){
        return ifNameAdded;
    }

}