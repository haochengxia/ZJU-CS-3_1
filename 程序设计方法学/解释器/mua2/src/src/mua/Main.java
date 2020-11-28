package src.mua;

import  src.mua.interpreter.Interpreter;

import java.util.Scanner;

/** current version file structure
 * └── src
 *     └── mua
 *         ├── dataType
 *         │   ├── Bool.java
 *         │   ├── List.java
 *         │   ├── MUAObject.java
 *         │   ├── None.java
 *         │   ├── Number.java
 *         │   └── Word.java
 *         ├── exception
 *         │   ├── ArgException.java
 *         │   ├── ArithmeticException.java
 *         │   ├── InputException.java
 *         │   ├── MUAException.java
 *         │   ├── NameException.java
 *         │   ├── SyntaxException.java
 *         │   └── TypeException.java
 *         ├── Expr.java
 *         ├── Func.java
 *         ├── interpreter
 *         │   ├── Interpreter.java
 *         │   └── Scope.java
 *         ├── Main.java
 *         ├── op
 *         │   ├── Erase.java
 *         │   ├── func
 *         │   │   ├── Export.java
 *         │   │   ├── Output.java
 *         │   │   └── Stop.java
 *         │   ├── Isname.java
 *         │   ├── judge
 *         │   │   ├── If.java
 *         │   │   ├── Isbool.java
 *         │   │   ├── Isempty.java
 *         │   │   ├── Islist.java
 *         │   │   ├── Isnumber.java
 *         │   │   └── Isword.java
 *         │   ├── Make.java
 *         │   ├── operator
 *         │   │   ├── Add.java
 *         │   │   ├── And.java
 *         │   │   ├── Div.java
 *         │   │   ├── Eq.java
 *         │   │   ├── Gt.java
 *         │   │   ├── Lt.java
 *         │   │   ├── Mod.java
 *         │   │   ├── Mul.java
 *         │   │   ├── Not.java
 *         │   │   ├── Or.java
 *         │   │   └── Sub.java
 *         │   ├── Print.java
 *         │   ├── Read.java
 *         │   ├── Readlist.java
 *         │   ├── Repeat.java
 *         │   └── Thing.java
 *         └── utils
 *             ├── ArgUtil.java
 *             ├── ParserUtil.java
 *             ├── RunUtil.java
 *             └── TransUtil.java
 */


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
            interpreter.next(line,LineStarch);
        }
    }
}
