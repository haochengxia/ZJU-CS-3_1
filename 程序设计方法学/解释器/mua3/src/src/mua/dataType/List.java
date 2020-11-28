package src.mua.dataType;

import java.util.ArrayList;
/**
 * @Member: value
 * @Method: List
 * getTypeName
 * getValue
 * toString
 **/

public class List extends Object {

    private ArrayList<Object> value;

    public List(ArrayList<Object> list) {
        this.value = list;
    }

    @Override
    public String toString() {
        String content = "";
        boolean first = true;
        for (Object v : value) {
            content += (first ? "" : " ") + v.toString();
            first = false;
        }
        // change in 2020/1/6 [-]return "[" + content + "]";
        return "[" + content + "]";
    }

    public Object getValue(int index){
        return value.get(index);
    }

    public int getSize(){
        return value.size();
    }

    @Override
    public String getTypeName() {
        return "list";
    }

    @Override
    public ArrayList<Object> getValue() {
        return value;
    }


}