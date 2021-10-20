package com.hikvision.letcode;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/longest-palindromic-substring/
 * @date 2021/7/23 16:32
 */
public class Demo5 {
    public static void main(String[] args) {
        System.out.println(longestPalindromeTwo("cbbd"));
    }

    /**
     * 方式二：动态规划
     * @param string
     * @return
     */
    private static String longestPalindromeTwo(String string) {
        if (string == null || string.length() < 2) {
            return string;
        }
        int start = 0;
        int max = 1;
        char[] chars = string.toCharArray();
        int length = chars.length;
        boolean[][] bool = new boolean[length][length];
        IntStream.range(0, length).forEach(i -> bool[i][i] = true);
        for (int j = 1; j < length; j++) {
            for (int i = 0; i < j; i++) {
                if (chars[i] != chars[j]) {
                    bool[i][j] = false;
                } else {
                    bool[i][j] = j - i < 3 || bool[i + 1][j - 1];
                }
                if (bool[i][j] && (j - i + 1) > max) {
                    start = i;
                    max = j - i + 1;
                }
            }
        }
        return string.substring(start, max + start);
    }

    /**
     * 方式一：暴力破解
     * @param string
     * @return
     */
    private static String longestPalindrome(String string) {
        String result = "";
        for (int i = 0; i < string.length(); i++) {
            for (int j = string.length(); j > i; j--) {
                String sub = string.substring(i, j);
                if (isSuccess(sub) && sub.length() > result.length()) {
                    result = sub;
                }
            }
        }
        return result;
    }

    public static boolean isSuccess(String subStr) {
        char[] chars = subStr.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            if (chars[i] != chars[subStr.length() - i - 1]) {
                return false;
            }
        }
        return true;
    }

    /*public static String longestPalindrome(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                String item = s.substring(i, j);
                if (isSuccess(item) && item.length() > result.length()) {
                    result = item;
                }
            }
        }
        return result;
    }

    public static boolean isSuccess(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }*/
}
