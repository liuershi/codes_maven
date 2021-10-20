package com.hikvision.letcode;

import java.util.Arrays;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/remove-element/
 * @date 2021/7/27 22:34
 */
public class Demo27 {
    public static void main(String[] args) {
        int[] array = {0,1,2,2,3,0,4,2};
        final int length = removeElement(array, 2);
        for (int i = 0; i < length; i++) {
            System.out.println(array[i]);
        }
    }

    public static int removeElement(int[] nums, int val) {
        Arrays.sort(nums);
        final int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (nums[i] == val) {
                int l = i + 1, r = length - 1;
                nums[i] = nums[r];
                while (l < length && val == nums[l]) {
                    r--;
                    nums[l] = nums[r];
                    l++;
                }
                return length - (l - i);
            }
        }
        return nums.length;
    }
}
