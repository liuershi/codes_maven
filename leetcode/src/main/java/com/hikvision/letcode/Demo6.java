package com.hikvision.letcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/zigzag-conversion/
 * @date 2021/9/11 16:50
 */
public class Demo6 {
    public static void main(String[] args) {
        String content = "AB";
        System.out.println(convert(content, 1));
    }

    public static String convert(String s, int numRows) {
        int length = s.length();
        if (length <= numRows || numRows <= 1) {
            return s;
        }
        char[] content = s.toCharArray();

        List<StringBuilder> sbs = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            sbs.add(new StringBuilder());
        }
        int index = 0, flag = -1;
        for (int i = 0; i < length; i++) {
            sbs.get(index).append(content[i]);
            if (index == 0 || index == numRows - 1) {
                flag = -flag;
            }
            index += flag;
        }
        return sbs.stream().map(StringBuilder::toString).collect(Collectors.joining(""));
    }
}
