package com.hikvision.letcode;

import sun.misc.Regexp;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/regular-expression-matching
 * @date 2021/9/14 15:08
 */
public class Demo10 {
    public static void main(String[] args) {
        System.out.println(isMatch("mississippi", "mis*is*p*."));
    }

    public static boolean isMatch(String s, String p) {
        return s.matches(p);
    }
}
