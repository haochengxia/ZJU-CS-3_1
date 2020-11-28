package src.mua;

import  src.mua.interpreter.Interpreter;

import java.util.Scanner;

import static src.mua.interpreter.Interpreter.loadCommand;




public class Main {
    public static void main(String[] args) {
        //System.out.println("***MUA Interpreter v2.00 release on 12/12/2019***");
        //System.out.println("***Copyright@Haocheng Xia (appreciate the help of senior LZX)***");
        Interpreter interpreter = new Interpreter();
        Scanner LineStarch=new Scanner(System.in);
        while (true) {
            if (!LineStarch.hasNextLine()) break;
            String line = LineStarch.nextLine();
            //System.out.println(line);
//            if (!loadCommand.equals("no"))
//                interpreter.next("no",LineStarch);

            interpreter.next(line,LineStarch);

        }
    }
}
