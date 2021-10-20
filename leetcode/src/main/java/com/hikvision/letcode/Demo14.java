package com.hikvision.letcode;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/longest-common-prefix
 * @date 2021/9/15 15:24
 */
public class Demo14 {
    public static void main(String[] args) {
        String[] array = new String[]{"ab", "a"};
        System.out.println(longestCommonPrefix(array));
    }

    public static String longestCommonPrefix(String[] strs) {
        int length = strs.length;
        String first = strs[0];
        if(length < 2) {
            return first;
        }
        int index = 0;
        char[] chars = first.toCharArray();
        out:for (char c : chars) {
            for (int i = 1; i < length; i++) {
                String current = strs[i];
                if (index >= current.length()) {
                    break out;
                }
                char c2 = current.charAt(index);
                if (c != c2) {
                    break out;
                }
            }
            ++index;
        }
        if (index < 1) {
            return "";
        }
        return first.substring(0, index);
    }
}
