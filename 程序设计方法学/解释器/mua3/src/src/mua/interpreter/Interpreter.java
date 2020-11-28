package src.mua.interpreter;


import src.mua.Expression;
import src.mua.dataType.*;
import src.mua.dataType.Object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static src.mua.utils.ParserUtil.parseObj;
import static src.mua.utils.ParserUtil.parseToken;


public class Interpreter {


    public Interpreter() {
        this.global = new NameSpace();
        // TODO: improve this patch operation
        this.global.addName(new Word("pi"), new Word("3.14159"));
    }

    /**
     *
     * @param rawLine
     * @param LineStarch
     * @return
     * @throws Exception
     */
    public static String getLine(String rawLine,Scanner LineStarch) throws Exception {
        String line = getLineWithoutComment(rawLine);
        while (line.trim().equals("")) {
            line = getLineWithoutComment(line);
        }
        while (true) {
            if (line.startsWith("make")&& !line.endsWith("[")&&!line.endsWith("]"))
            // 另一种情况需要在读一行　那就是　make "length \n [ TODO stop sys out
            {

                ArrayList<String> list=new ArrayList<String>(Arrays.asList(line.split("\\s")));
                //System.out.println("the real line2: " + line);
            for (int i = 0;i<2;i++){
                //System.out.println("split: "+list.get(i) );
            }
            if (list.get(0).equals("make")&& list.get(list.size()-1).startsWith("\"") && !list.get(list.size()-1).endsWith("[")&&!list.get(list.size()-1).endsWith("]")){
                String temp = getLineWithoutComment(LineStarch.nextLine());
                //System.out.println("get next line: " + temp);
                line += " " + temp;
                //System.out.println("the real line: " + line);
                //continue;
            }
            }

            boolean inWord = false;
            int count = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '"')
                    inWord = true;
                else if (Character.isWhitespace(line.charAt(i))
                        || (count > 0 && line.charAt(i) == ']'))
                    inWord = false;
                if (line.charAt(i) == '[' && !inWord) {
                    count++;
                } else if (line.charAt(i) == ']' && !inWord) {
                    count--;
                }
            }
            if (count != 0) {
                // we need to get the next line
                String temp = getLineWithoutComment(LineStarch.nextLine());
                line += " " + temp;
                continue;
            } else
                break;
        }
        return line;

    }

    /**
     *
     * @param line
     * @throws Exception
     */
    public void calculateLine(String line) throws Exception {
        ArrayList<String> tokens = parseToken(line);
        ArrayList< src.mua.dataType.Object> objList = parseObj(tokens, global);
        if (objList.size() != 1) {

        } else {
            src.mua.dataType.Object obj = objList.get(0);
            if (obj instanceof Expression) {
                src.mua.dataType.Object ret = ((Expression) obj).calculate(global);
                if (ret instanceof None){
                    None none = (None)ret;
                    if (none.ifNameAdded().equals("true")){
                        // update the namespace
//                        System.out.println("In this part" + none.noneNameSpace.toString());
//                        global.nameSpace.putAll(none.noneNameSpace);
                        // TODO STOP sys out
                        // System.out.println("In this part"+none.test);
                    }
                }
                if (!(ret instanceof None)) {
                    System.out.println(ret);
                }
            } else {
                System.out.println(obj);
            }
        }
    }

    /**
     *
     * @param line
     * @return
     */
    private static String getLineWithoutComment(String line) {
        int i = line.indexOf("//");
        if (i != -1) {
            line = line.substring(0, i);
        }
        return line;
    }

    /**
     *
     * @param line
     * @param LineStarch
     */
    public void next(String line,Scanner LineStarch) {
        try {
            // before that we need to format the line
            String formattedLine = getLine(line,LineStarch);
            calculateLine(formattedLine);
            if (!loadCommand.equals("nothing")) {
                //System.out.println("do one");
                calculateLine(formattedLine);
                ArrayList<String> arrayList  = new ArrayList(Arrays.asList(loadCommand.split("\\n")));
                for (String s : arrayList) calculateLine(s);
                loadCommand = "nothing";
            }

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public NameSpace global = new NameSpace();
    public  static String loadCommand = "nothing";
}