package com.hikvision.letcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/integer-to-roman
 * @date 2021/9/14 16:00
 */
public class Demo12 {
    public static void main(String[] args) {
        System.out.println(intToRoman(1994));
    }

    public static String intToRoman(int num) {
        int[] ints = Arrays.stream(String.valueOf(num).split("")).mapToInt(Integer::parseInt).toArray();
        int length = ints.length;
        StringBuilder sb = new StringBuilder();
        for (int i : ints) {
            if (i == 0) {
                --length;
                continue;
            }
            switch (length) {
                case 4:
                    IntStream.range(0, i).forEach(x -> sb.append("M"));
                    break;
                case 3:
                    execute(i, sb, "D", "C", "M");
                    break;
                case 2:
                    execute(i, sb, "L", "X", "C");
                    break;
                case 1:
                    execute(i, sb, "V", "I", "X");
                    break;
                default:
                    break;
            }
            --length;
        }

        return sb.toString();
    }

    private static void execute(int i, StringBuilder sb, String prev, String current,String prevPrev) {
        int x = i / 5;
        int y = i % 5;
        if (x > 0) {
            if (i == 9) {
                sb.append(current).append(prevPrev);
            } else {
                sb.append(prev);
                IntStream.range(0, y).forEach(z -> sb.append(current));
            }
        } else {
            if (i == 4) {
                sb.append(current).append(prev);
            } else {
                IntStream.range(0, y).forEach(z -> sb.append(current));
            }
        }
    }
}
