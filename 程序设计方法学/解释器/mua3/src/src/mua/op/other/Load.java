package src.mua.op.other;

import src.mua.Expression;
import src.mua.dataType.None;
import src.mua.dataType.Word;
import src.mua.interpreter.Interpreter;
import src.mua.interpreter.NameSpace;
import src.mua.utils.ArgumentUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static src.mua.interpreter.Interpreter.loadCommand;

/**
 * load added in 12/27/2019
 */

public class Load extends Expression {
    public static final int firstArg = 0;
    public static final int secondArg = 1;
    public static final int thirdArg = 2;

    public static final int firstObj = 0;
    public static final int secondObj = 1;
    public static final int thirdObj = 2;

    @Override
    public String getOpName() {
        return "load";
    }

    @Override
    public None calculate(NameSpace nameSpace) throws Exception {
        super.calculate(nameSpace);
        ArgumentUtil.argCheck(getOpName(), argTypes, argList);

        Word word = (Word)argList.get(firstArg);
        File file = new File(word.getValue());
        String name = word.getValue();

        FileInputStream input = null;
        ByteArrayOutputStream output = null;
        // get file input
        try {
            input = new FileInputStream(name);
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n;
            while (-1 != (n=input.read(buffer))){
                output.write(buffer, 0,n);
            }

            loadCommand = new String(output.toByteArray());
            //None none = new None(nameSpace.nameSpace);
            None none = new None("noting");
            return none;
        }
        catch ( IOException e){
            e.printStackTrace();
        }finally {
            input.close();
            output.close();
        }
        return new None();
    }


    final static private ArrayList<Class> argTypes = new ArrayList<Class>(Arrays.asList(
            Word.class
    ));

    public int getArgNum() {
        return argTypes.size();
    }
}
