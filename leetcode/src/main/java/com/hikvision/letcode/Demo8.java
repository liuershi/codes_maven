package com.hikvision.letcode;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/string-to-integer-atoi
 * @date 2021/9/13 21:12
 */
public class Demo8 {
    public static void main(String[] args) {
        System.out.println(myAtoi("91283472332"));
    }

    public static int myAtoi(String s) {
        int length = s.length();
        if (length <= 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int index = 0;
        while (index < length && chars[index] == ' ') {
            index++;
        }
        // 极端情况，即s为空字符串
        if (index == length) {
            return 0;
        }
        int flag = 1;
        if (chars[index] == '-') {
            index++;
            flag = -flag;
        } else if (chars[index] == '+') {
            index++;
        }
        if (index >= length) {
            return 0;
        }
        if (chars[index] > '9' || chars[index] < '0') {
            return 0;
        }
        int rep = 0;
        while (index < length) {
            char c = chars[index];
            if (c > '9' || c < '0') {
                break;
            }
            if (rep > Integer.MAX_VALUE / 10 || (rep == Integer.MAX_VALUE / 10 && (c - '0') > Integer.MAX_VALUE % 10)) {
                return Integer.MAX_VALUE;
            }
            if (rep < Integer.MIN_VALUE / 10 || (rep == Integer.MIN_VALUE / 10 && (c - '0') > -(Integer.MIN_VALUE % 10))) {
                return Integer.MIN_VALUE;
            }
            rep = rep * 10 + (c - '0') * flag;

            ++index;
        }
        return rep;
    }
}
