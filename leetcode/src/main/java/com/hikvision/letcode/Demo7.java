package com.hikvision.letcode;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/reverse-integer
 * @date 2021/9/12 15:40
 */
public class Demo7 {
    public static void main(String[] args) {
        System.out.println(reverse(Integer.MIN_VALUE));
    }

    public static int reverse(int x) {
        boolean flag = false;
        if (x < 0) {
            flag = true;
        }
        String s = new StringBuilder(String.valueOf(x).substring(flag?1:0)).reverse().toString();
        try {
            int i = Integer.parseInt(s);
            return flag ? -i : i;
        } catch (NumberFormatException e) {
        }
        return 0;
    }
}
