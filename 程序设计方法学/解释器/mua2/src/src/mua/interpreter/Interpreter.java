package src.mua.interpreter;


import src.mua.Expr;
import src.mua.exception.MUAException;
import src.mua.exception.SyntaxException;
import src.mua.dataType.*;

import java.util.ArrayList;
import java.util.Scanner;

import static src.mua.utils.ParserUtil.parseObj;
import static src.mua.utils.ParserUtil.parseToken;


public class Interpreter {


    public Interpreter() {
        this.global = new Scope();

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
                if (count < 0) {
                    throw new SyntaxException("lost ']'");
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
    public void evalLine(String line) throws Exception {
        ArrayList<String> tokens = parseToken(line);
        ArrayList<MUAObject> objlist = parseObj(tokens, global);
        if (objlist.size() != 1) {
            throw new SyntaxException("more than one object in one line");
        } else {
            MUAObject obj = objlist.get(0);
            if (obj instanceof Expr) {
                MUAObject ret = ((Expr) obj).eval(global);
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
            evalLine(formattedLine);
        } catch (MUAException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Scope global = new Scope();
}