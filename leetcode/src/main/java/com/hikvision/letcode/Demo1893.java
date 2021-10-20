package com.hikvision.letcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/check-if-all-the-integers-in-a-range-are-covered/
 * @date 2021/7/23 18:09
 */
public class Demo1893 {
    public static void main(String[] args) {
        int[][] ranges = {{25,42},{7,14},{2,32},{25,28},{39,49},{1,50},{29,45},{18,47}};
        System.out.println(isCovered(ranges, 15, 38));
    }

    public static boolean isCovered(int[][] ranges, int left, int right) {
        int result = 0;
        for (int i = left; i <= right; i++) {
            for (int[] array : ranges) {
                if (array.length > 0 && i >= array[0] && i <= array[array.length - 1]) {
                    ++result;
                    System.out.println(i);
                    break;
                }
            }
        }
        return result == (right - left + 1);
    }
}
