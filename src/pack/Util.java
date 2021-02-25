package pack;

import java.util.Arrays;

public class Util {
    public static String ls = System.getProperties().getProperty("line.separator");

    public static int[] concat(int[] first, int[] second) {
        int[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }



}