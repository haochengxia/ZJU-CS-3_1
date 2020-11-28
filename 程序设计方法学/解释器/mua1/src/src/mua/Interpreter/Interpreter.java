package src.mua.Interpreter;

import java.util.*;

/**
 * file: MyInterpreter.java
 * function: The implement of the interpreter part1
 * Author: @haochengxia
 */

class MuaTypeException extends RuntimeException {
}

interface Calculate {
    Value Run();
}

abstract class Value {

    abstract public void print();

    abstract public String getString() throws MuaTypeException;

    abstract public Float getFloat() throws MuaTypeException;

    abstract public Boolean getBoolean() throws MuaTypeException;
}

class Word extends Value {
    private String wordValue;

    //private String wordName;
    // init
    //word(){
    /* empty */
    //}
    /* Word(String wordName, String wordValue) {
        this.wordName = wordName;
        this.wordValue = wordValue;
    }*/
    // functions
    public Word set(String wordValue) {
        this.wordValue = wordValue;
        return this;
    }

    public String getString() {
        return wordValue;
    }

    public Float getFloat() throws MuaTypeException {
        int flag = 1;// if the value of the word is matched with the number format, automatically translate
        Float num = new Float(0.0);
        int dotCount = 0;
        int dotPosition = 0;
        if (wordValue.charAt(0) <= '9' && wordValue.charAt(0) >= '0') //which means that the word has the possible that is a num
        {

            for (int i = 1; i <= wordValue.length(); i++) {
                if (wordValue.charAt(i - 1) <= '9' && wordValue.charAt(i - 1) >= '0' || wordValue.charAt(i - 1) == '.') {
                    if (wordValue.charAt(i - 1) != '.') num = (num * 10 + (wordValue.charAt(i - 1) - '0'));
                    else {
                        dotCount++;
                        dotPosition = i;
                    }
                } else {
                    flag = 0;
                    break;
                }
            }
        } else {
            flag = 0;
        }
        if (flag == 1 && dotCount <= 1) {
            if (dotCount == 1) {
                if (dotPosition == wordValue.length()) return num;
                else {
                    for (int i = 0; i < (wordValue.length() - dotPosition); i++)
                        num = num / 10;
                }
            } else return num;
        }
        throw new MuaTypeException();
    }

    public Boolean getBoolean() throws MuaTypeException {
        throw new MuaTypeException();
    }

    @Override
    public void print() {
        System.out.println(wordValue);
    }
}

class Number extends Value {
    private float numberValue;

    public Number set(float numberValue) {
        this.numberValue = numberValue;
        return this;
    }

    public String getString() throws MuaTypeException {
        throw new MuaTypeException();
    }

    public Float getFloat() {
        return numberValue;
    }

    public Boolean getBoolean() throws MuaTypeException {
        throw new MuaTypeException();
    }


    @Override
    public void print() {
        System.out.println(numberValue);
    }
}

class List extends Value {
    ArrayList<Value> listValue = new ArrayList<>();

    public void append(Value listValue) {
        this.listValue.add(listValue);
    }

    public String getString() throws MuaTypeException {
        throw new MuaTypeException();
    }

    public Float getFloat() throws MuaTypeException {
        throw new MuaTypeException();
    }

    public Boolean getBoolean() throws MuaTypeException {
        throw new MuaTypeException();

    }

    @Override
    public void print() {
        System.out.println("<List:");
        for (Value v : listValue) {
            v.print();
            //System.out.println(v.getString()+"this");
        }
        System.out.println(">");
    }
}

class Bool extends Value {
    private Boolean boolValue;

    public Bool set(Boolean boolValue) {
        this.boolValue = boolValue;
        return this;
    }

    public String getString() throws MuaTypeException {
        throw new MuaTypeException();
    }

    public Float getFloat() throws MuaTypeException {
        throw new MuaTypeException();
    }

    public Boolean getBoolean() {
        return boolValue;
    }

    @Override
    public void print() {
        System.out.println(boolValue);
    }

}


public class Interpreter {
    private Scanner LineStarch = new Scanner(System.in);
    private Scanner scan;
    private HashMap<String, Value> varDict = new HashMap<>();
    private HashMap<String, Calculate> opMethod = new HashMap<>();
    private ArrayList operation = new ArrayList<String>() {
        {
            add("make");
            add("print");
            add("erase");
            add("make");
        }
    };

    public Interpreter() {
        opMethod.put("add", new Calculate() {
            @Override
            public Number Run() {
                Number result = new Number();
                Float tmp = getValue().getFloat() + getValue().getFloat();
                result.set(tmp);
                return result;
            }
        });
        opMethod.put("sub", new Calculate() {
            @Override
            public Number Run() {
                Number result = new Number();
                Float tmp = getValue().getFloat() - getValue().getFloat();
                result.set(tmp);
                return result;
            }
        });
        opMethod.put("mul", new Calculate() {
            @Override
            public Number Run() {
                Number result = new Number();
                Float tmp = getValue().getFloat() * getValue().getFloat();
                result.set(tmp);
                return result;
            }
        });

        opMethod.put("div", new Calculate() {
            @Override
            public Number Run() {
                Number result = new Number();
                Float dividend = getValue().getFloat();
                Float dividor = getValue().getFloat();
                if (dividor == 0) System.out.println("Error: Illegal dividor ! ");
                result.set(dividend / dividor);
                return result;
            }
        });

        opMethod.put("mod", new Calculate() {
            @Override
            public Number Run() {
                Number result = new Number();
                Float dividend = getValue().getFloat();
                Float divider = getValue().getFloat();
                if (divider == 0) System.out.println("Error: Illegal divider ! ");
                result.set(dividend % divider);
                return result;
            }
        });

        opMethod.put("eq", new Calculate() {
            @Override
            public Bool Run() {
                Value oprand1 = getValue();
                Value oprand2 = getValue();
                if (oprand1 instanceof Word && oprand2 instanceof Word) {
                    // when two oprands are both Word type
                    return new Bool().set(oprand1.getString().equals(oprand1.getString()));
                } else if (oprand1 instanceof Number && oprand2 instanceof Number) {
                    // when two oprands are both Number type
                    return new Bool().set(oprand1.getFloat().equals(oprand2.getFloat()));
                }
                // if two oprands hold different type, then the result must be false
                return new Bool().set(false);
            }
        });

        opMethod.put("lt", new Calculate() {
            @Override
            public Bool Run() {
                Value oprand1 = getValue();
                Value oprand2 = getValue();
                if (oprand1 instanceof Word && oprand2 instanceof Word) {
                    int bound = oprand1.getString().compareTo(oprand2.getString());
                    if (bound < 0) {
                        return new Bool().set(true);
                    } else {
                        return new Bool().set(false);
                    }
                } else if (oprand1 instanceof Number && oprand2 instanceof Number) {
                    int bound = oprand1.getFloat().compareTo(oprand2.getFloat());
                    if (bound < 0) {
                        return new Bool().set(true);
                    } else {
                        return new Bool().set(false);
                    }
                }
                // different type can't be compared
                return null;
            }
        });

        opMethod.put("gt", new Calculate() {
            @Override
            public Bool Run() {
                return new Bool().set(!opMethod.get("lt").Run().getBoolean());
            }
        });

        opMethod.put("and", new Calculate() {
            @Override
            public Bool Run() {
                return new Bool().set(getValue().getBoolean() && getValue().getBoolean());
            }
        });

        opMethod.put("or", new Calculate() {
            @Override
            public Bool Run() {
                return new Bool().set(getValue().getBoolean() || getValue().getBoolean());
            }
        });

        opMethod.put("not", new Calculate() {
            @Override
            public Bool Run() {
                return new Bool().set(!getValue().getBoolean());
            }
        });

    }

    /*-------------------------------------------------Func: getValue----------------------------------------------------*/

    private Value getValue(String op) {
        /* charAt() 方法用于返回指定索引处的字符。索引范围为从 0 到 length() - 1 */

        //字的字面以"开头
        if (op.charAt(0) == '"') {
            //创建字
            return new Word().set(op.substring(1, op.length()));
        }
        //表的字面量以[开头,以]结束
        if (op.equals("[")) {
            List result = new List();
            int cnt = 1;
            while (true) {
                String Str = scan.next();
                Value nextElement;
                //if we find another [ in the oprateStr, go to the next level
                if (Str.equals("[")) {
                    nextElement = getValue(Str);
                    cnt++;
                } else if (Str.equals("]")) {
                    return result;
                } else {
                    try {
                        nextElement = new Number().set(Float.parseFloat(Str));
                    } catch (java.lang.NumberFormatException e) {
                        if (Str.charAt(0) == '"') {
                            nextElement = new Word().set(Str.substring(1, Str.length()));
                        } else if (opMethod.containsKey(Str) || operation.contains(Str)) {
                            nextElement = new Word().set(Str);
                        } else {
                            throw new MuaTypeException();
                        }
                    }
                }
                result.append(nextElement);
            }
        }

        if (op.equals("read")) {
            //System.out.print("> ");
            Scanner temp_scan = scan;
            scan = new Scanner(LineStarch.nextLine());
            //Value result = getValue();
            String tmp = scan.nextLine();
            scan = temp_scan;

            //Float tmp;
            //if (result.getFloat()!=null) {
            // tmp = result.getFloat();
            return new Word().set((tmp+"").substring(0,(tmp+"").length()));
            //}
            // else return null;
        }

        if (op.equals("readlist")) {
            //System.out.print("> ");
            Scanner temp_scan = scan;

            String line = LineStarch.nextLine();
            scan = new Scanner(line.replace("[", " [ ").replace("]", " ] "));
            List list = new List();
            while (scan.hasNext()) {
                list.append(getValue());
            }
            scan = temp_scan;
            return list;
        }

        if (op.equals("thing") || op.charAt(0) == ':') {
            String key;
            if (op.equals("thing")) {
                key = getValue().getString();
            } else {
                key = op.substring(1, op.length());
            }
            Value boundValue = varDict.get(key);
            if (boundValue == null) {
                System.out.println("Error: No value bound with this word.");
            } else {
                return boundValue;
            }
        }

        if (op.equalsIgnoreCase("true") || op.equalsIgnoreCase("false")) {
            return new Bool().set(Boolean.parseBoolean(op));
        }

        if (op.equals("isname")) {
            String key = scan.next();
            if (key.charAt(0) != '"') {
                System.out.println("Error: Illegal word.");
            } else {
                return new Bool().set(varDict.containsKey(key.substring(1, key.length())));
            }
        }
        try {
            return new Number().set(Float.parseFloat(op));
        } catch (java.lang.NumberFormatException e) {
            if (opMethod.containsKey(op)) {
                return opMethod.get(op).Run();
            }
        }

        return null;
    }

    private Value getValue() {
        String op = scan.next();
        return getValue(op);
    }

    public void Run() {
        //String PS1 = "Percy's Mua> ";
        while (true) {
            //System.out.print(PS1);
            if (!LineStarch.hasNextLine()) break;
            scan = new Scanner(LineStarch.nextLine().replace("[", " [ ").replace("]", " ] "));
            //if (!LineStarch.hasNextLine()) break;
            //if (!LineStarch.hasNext()) break;
            // if (!scan.hasNext()) {
            // continue;
            // }
            String op = scan.next();

            if (op.equals("exit")) {
                //System.out.println("Bye~");
                break;
            }
            else if (op.length() >= 2 && op.substring(0, 2).equals("//")) {
                LineStarch.nextLine();
                continue;
            }
            if (!op.equals("exit") && !op.equals("print") && !op.equals("make") && !op.equals("erase") ) break;
            if (op.equals("print")) {
                Value v = null;
                try {
                    v = getValue();
                } catch (java.util.NoSuchElementException e) {
                    // System.out.println("Error: Incomplete input. Please check.");
                } catch (MuaTypeException e) {
                    //System.out.println("Error: TypeError. Please check.");
                }
                if (v == null) {
                    // System.out.println("Error: Parse failed. Please check syntax.");
                } else {
                    // if (v.getFloat() > 200) System.out.println((v.getString()+"this");
                    v.print();
                }

            }
            if (op.equals("make")) {
                String key = scan.next();
                if (key.charAt(0) != '"') {
                    //System.out.println("Error: Illegal word");
                } else {
                    varDict.put(key.substring(1, key.length()), getValue());
                }
            }
            if (op.equals("erase")) {

                String key = scan.next();
                if (key.charAt(0) != '"') {
                    // System.out.println("Error: Illegal word");
                } else if (!varDict.containsKey(key.substring(1, key.length()))) {
                    // System.out.println("No value bound with this word.");
                } else {
                    Iterator<String> it = varDict.keySet().iterator();
                    while (it.hasNext()) {
                        String k = it.next();
                        if (k.equals(key.substring(1, key.length()))) {
                            it.remove();
                            varDict.remove(k);
                        }
                    }
                }
            }
            //if (!scan.hasNextLine()) break;

        }
    }

}