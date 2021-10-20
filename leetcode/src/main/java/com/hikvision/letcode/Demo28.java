package com.hikvision.letcode;

import java.util.Arrays;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/implement-strstr/
 * @date 2021/9/22 20:19
 */
public class Demo28 {
    public static void main(String[] args) {
        System.out.println(kmpMatch("BBC ABCDAB ABCDABCDABDE", "ABCDABD"));
    }

    /**
     * 第三种：kmp算法
     * @param haystack
     * @param needle
     * @return
     */
    public static int kmpMatch(String haystack, String needle) {
        if (haystack.equals(needle) || "".equals(needle)) {
            return 0;
        }
        int[] next = kmpNext(needle);
        for (int i = 0, j = 0; i < haystack.length(); i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                ++j;
            }
            if (j == needle.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    /**
     * 获取模板字符串对应的匹配表
     * @param needle
     * @return
     */
    public static int[] kmpNext(String needle) {
        int[] next = new int[needle.length()];
        for (int i = 1, j = 0; i < needle.length(); i++) {
            // 2.不相同
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }
            // 1. 如果相等则累加
            if (needle.charAt(i) == needle.charAt(j)) {
                ++j;
            }
            next[i] = j;
        }
        return next;
    }

    /**
     * 第二种暴力破解法
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStrTwo(String haystack, String needle) {
        int hLength = haystack.length();
        int nLength = needle.length();

        int i = 0, j = 0;
        while (i < hLength && j < nLength) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                ++i;
                ++j;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == nLength) {
            return i - j;
        }
        return -1;
    }

    /**
     * 第一种暴力破解法
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {
        if (needle.length() > haystack.length()) {
            return -1;
        }
        if (haystack.equals(needle) || "".equals(needle)) {
            return 0;
        }
        int start = -1;
        char[] chars = haystack.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == needle.charAt(0)) {
                start = i;
                if (start + needle.length() > haystack.length()) {
                    return -1;
                }
                char[] array = needle.toCharArray();
                for (int j = 1; j < array.length; j++) {
                    if (chars[i + j] != array[j]) {
                        start = -1;
                        break;
                    }
                }
            }
            if (start >= 0) {
                break;
            }
        }
        return start;
    }
}
