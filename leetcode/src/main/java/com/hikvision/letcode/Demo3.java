package com.hikvision.letcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 * @date 2021/7/22 20:06
 */
public class Demo3 {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("dvdf"));
    }

    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int sum = 0;
        for (int start = 0, end = 0; end < s.length(); end++) {
            char item = s.charAt(end);
            if (map.containsKey(item)) {
                start = Math.max(start, map.get(item) + 1);
            }
            sum = Math.max(sum, end - start + 1);
            map.put(item, end);
        }
        return sum;
    }
}
