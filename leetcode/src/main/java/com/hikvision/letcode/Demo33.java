package com.hikvision.letcode;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
 * @date 2021/9/24 11:46
 */
public class Demo33 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 3};
        System.out.println(searchTwo(nums, 4));
    }

    public static int search(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int searchTwo(int[] nums, int target) {
        int start = 0;
        int end = nums.length;
        while (end - start > 2) {
            int index = (end - start) / 2;
            // 此时为有序数组
            if (nums[start] < nums[index]) {
                if (target >= nums[start] && target <= nums[index]) {
                    end = index;
                }
            } else {
                index = (start + index) / 2;
            }
        }
        return -1;
    }

    public static int cycle(int start, int end, int[] nums, int target) {
        int index = (start + end) / 2;
        if (target >= nums[start] && target <= nums[end]) {
            if (target == nums[index]) {
                return index;
            } else if (target > nums[index]) {
                return cycle(index + 1, end, nums, target);
            } else {
                return cycle(start, index - 1, nums, target);
            }
        } else {
            for (int i = index; i < nums.length; i++) {
                if (nums[i] == target) {
                    return i;
                }
            }
        }
        return -1;
    }
}

