package com.hikvision.letcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/roman-to-integer
 * @date 2021/9/15 13:44
 */
public class Demo13 {
    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));
    }

    public static int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<String, Integer>(){{
            put("I", 1);
            put("IV", 4);
            put("V", 5);
            put("IX", 9);
            put("X", 10);
            put("XL", 40);
            put("L", 50);
            put("XC", 90);
            put("C", 100);
            put("CD", 400);
            put("D", 500);
            put("CM", 900);
            put("M", 1000);
        }};
        int sum = 0;
        String[] strings = s.split("");
        for (int i = 0; i < strings.length; i++) {
            if (i < strings.length - 1 && map.containsKey(strings[i] + strings[i + 1])) {
                sum += map.get(strings[i] + strings[i + 1]);
                ++i;
                continue;
            }
            sum += map.get(strings[i]);
        }
        return sum;
    }
}
