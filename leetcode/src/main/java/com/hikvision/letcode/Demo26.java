package com.hikvision.letcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 * @date 2021/7/27 21:17
 */
public class Demo26 {
    public static void main(String[] args) {
        int[] array = {0,0,1,1,1,2,2,3,3,4};
        final int length = method2(array);
        for (int i = 0; i < length; i++) {
            System.out.println(array[i]);
        }
    }

    private static int method2(int[] nums) {
        if (nums == null || nums.length == 1) {
            return nums == null ? 0 : nums.length;
        }
        int i = 0, j = 1;
        while (i < j && j < nums.length) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
            j++;
        }
        return ++i;
    }

    /**
     * 最简单的使用List判断
     * @param nums
     * @return
     */
    private static int method1(int[] nums) {
        List<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (!duplicates.contains(nums[i])) {
                duplicates.add(nums[i]);
            }
        }
        for (int i = 0; i < duplicates.size(); i++) {
            nums[i] = duplicates.get(i);
        }
        return duplicates.size();
    }
}
