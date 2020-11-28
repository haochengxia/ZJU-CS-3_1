package src.mua.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InfixUtil {
    public List<String> inTransPre(String infixStr) {

        //System.out.println("Total raw String" + infixStr);
        infixStr = infixStr.replace("(-","(0-");
        infixStr = infixStr.replace("+-","+0-");
        StringBuilder stringBuilder = new StringBuilder(infixStr.trim());
        // 统计有多少个运算符就可以知道参数的数量
        int validC = 0;
        char[] input = infixStr.toCharArray();
        for (int i = 0; i < infixStr.length(); i++) {
            if (input[i] == '+' || input[i] == '-' || input[i] == '*' || input[i] == '/') {
                if (input[i - 1] != '+' || input[i - 1] != '-' || input[i - 1] != '*' | input[i - 1] != '/') {
                    validC++;
                }
            }
        }
        stringBuilder = stringBuilder.reverse();
        List<String> pnStack = new ArrayList();//前缀表达式栈
        List<String> opStack = new ArrayList();//运算符栈

        String flag = "";
        char[] str = stringBuilder.toString().toCharArray();
        for (int i = 0; i < str.length; i++){
            // TODO
            //System.out.println("str" + str[i]);
            if (str[i] == ' ')  str[i] = '@';
        }
        //从左往右扫描
        for (int i = 0; i < str.length; i++) {
            if (isNotOp((flag + str[i]).trim())) {//判断flag+当前字符是否为数字
                flag = (flag + str[i]).trim();
                if (i == str.length - 1) {//当当前字符是数字，并且是最后一个字符时直接存入前缀表达式栈
                    //由于之前反转了，现在要反转回来，比如45在之前被反转为了54，需要反转回来
                    pnStack.add(new StringBuilder(flag).reverse().toString());
                }
            } else {//当当前字符不是数字时
                if (flag.trim() != "" && isNotOp((flag).trim())) {//将上一次的flag存入前缀表达式栈
                    pnStack.add(new StringBuilder(flag).reverse().toString());
                    flag = "";
                }
                if (opStack.size() == 0 || opStack.get(opStack.size() - 1).equals(")")) {//对应4.1
                    opStack.add(String.valueOf(str[i]));
                } else if (str[i] == ')') {//对应5.1
                    opStack.add(String.valueOf(str[i]));
                } else if (str[i] == '(') {//对应5.2
                    while (opStack.size() != 0) {
                        if (opStack.get(opStack.size() - 1).equals(")")) {
                            opStack.remove(opStack.size() - 1);
                            break;
                        }
                        pnStack.add(opStack.get(opStack.size() - 1));
                        opStack.remove(opStack.size() - 1);
                    }
                } else if (str[i] == '*' || str[i] == '/'|| str[i] == '%') {//对应4.2
                    opStack.add(String.valueOf(str[i]));
                } else if (opStack.get(opStack.size() - 1).equals("*") || opStack.get(opStack.size() - 1).equals("/")||opStack.get(opStack.size() - 1).equals("%")) {//对应4.3
                    pnStack.add(opStack.get(opStack.size() - 1));
                    opStack.remove(opStack.size() - 1);
                    i--;
                } else {//对应4.2
                    opStack.add(String.valueOf(str[i]));
                }
            }
        }
        //对应7
        while (opStack.size() != 0) {
            pnStack.add(opStack.get(opStack.size() - 1));
            opStack.remove(opStack.size() - 1);
        }
        //反转List对象
        Collections.reverse(pnStack);
        // TODO sys out mark
         //System.out.print("波兰式(前缀表达式)：");
        // 对加减乘除做替换
        for (int i =0;i<pnStack.size();i++){
            if (pnStack.get(i).contains("@"))
            {
                String tmp[] = pnStack.get(i).split("@");
                // TODO sys out mark
                //System.out.println("tmp"+tmp);
                pnStack.remove(i);
                List<String> list = Arrays.asList(tmp);
                pnStack.addAll(i,list);
            }

            switch (pnStack.get(i)){
                case "+": pnStack.set(i,"add"); break;
                case "-": pnStack.set(i,"sub"); break;
                case "*": pnStack.set(i,"mul"); break;
                case "/": pnStack.set(i,"div"); break;
                case "%": pnStack.set(i,"mod"); break;
            }



        }
        // TODO sys out mark
        //pnStack.stream().forEach(x -> System.out.print(x + " "));//迭代输出
        //System.out.println("");
        return pnStack;
    }

    public static boolean isNotOp(String str){
        String regex = "\\+|(?<=\\d)-|\\*|/|&|=|(>=)|(<=)";
        return str.matches("[@:_a-zA-Z0-9]{1,}") ;
    }
}