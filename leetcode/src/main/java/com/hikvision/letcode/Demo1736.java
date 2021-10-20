package com.hikvision.letcode;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/latest-time-by-replacing-hidden-digits/
 * @date 2021/7/24 21:26
 */
public class Demo1736 {
    public static void main(String[] args) {
        System.out.println(maximumTime("??:23"));
    }

    public static String maximumTime(String time) {
//        return test1(time);
        return test2(time);
    }

    private static String test2(String time) {
        final char[] chars = time.toCharArray();
        if (chars[0] == '?') {
            chars[0] = (chars[1] >= '4' && chars[1] <= '9') ? '1' : '2';
        }
        if (chars[1] == '?') {
            chars[1] = chars[0] >= '2' ? '3' : '9';
        }
        if (chars[3] == '?') {
            chars[3] = '5';
        }
        if (chars[4] == '?') {
            chars[4] = '9';
        }
        return new String(chars);
    }

    /**
     * if else 的方式比较垃圾
     * @param time
     * @return
     */
    private static String test1(String time) {
        if (!time.contains("?")) {
            return time;
        }
        final String[] times = time.split(":");
        String hour = times[0];
        if (hour.contains("?")) {
            if ("??".equals(hour)) {
                hour = "23";
            } else if ('?' == hour.charAt(0)) {
                String two = hour.substring(1);
                if (Integer.parseInt(two) < 4) {
                    hour = "2" + two;
                } else {
                    hour = "1" + two;
                }
            } else if ('?' == hour.charAt(1)) {
                String one = hour.substring(0, 1);
                if (Integer.parseInt(one) < 2) {
                    hour = one +"9";
                } else {
                    hour = one + "3";
                }
            }
        }
        times[0] = hour;
        String minuet = times[1];
        if ("??".equals(minuet)) {
            minuet = "59";
        } else if ('?' == minuet.charAt(0)) {
            minuet = "5" + minuet.substring(1);
        } else if ('?' == minuet.charAt(1)) {
            minuet = minuet.charAt(0) + "9";
        }
        times[1] = minuet;
        return String.join(":", times);
    }
}
