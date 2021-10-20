package com.hikvision.letcode;

import java.util.Arrays;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/next-permutation/
 * @date 2021/7/28 21:47
 */
public class Demo31 {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,6,5};
        nextPermutation(array);
        for (int i : array) {
            System.out.println(i);
        }
    }

    public static void nextPermutation(int[] nums) {
        int len = nums.length;
        for (int i = len - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                Arrays.sort(nums, i, len);
                for (int j = i; j <len; j++) {
                    if (nums[j] > nums[i - 1]) {
                        int temp = nums[j];
                        nums[j] = nums[i - 1];
                        nums[i - 1] = temp;
                        return;
                    }
                }
            }
        }
        Arrays.sort(nums);
    }
}
