package src.mua.utils;

import java.io.*;

public class TransUtil {

    public static final double INFINITY = 1E100;

    public static double translate(String wordValue) {
        int flag = 1;// if the value of the word is matched with the number format, automatically translate
        double num = 0;
        int dotCount = 0;
        int dotPosition = 0;
        if (wordValue.charAt(0) <= '9' && wordValue.charAt(0) >= '0') //which means that the word has the possible that is a num
        {

            for (int i = 1; i <= wordValue.length(); i++) {
                if (wordValue.charAt(i - 1) <= '9' && wordValue.charAt(i - 1) >= '0' || wordValue.charAt(i - 1) == '.') {
                    if (wordValue.charAt(i - 1) != '.') num = (num * 10.0 + (wordValue.charAt(i - 1) - '0'));
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
                    return num;
                }
            } else return num;
        }
        return INFINITY;
    }


    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {
        T clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clonedObj;
    }
}
