package com.hikvision.letcode;

import java.util.Arrays;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/3sum-closest/
 * @date 2021/7/27 16:20
 */
public class Demo16 {
    public static void main(String[] args) {
        int[] array = {-1,0,1,1,55};
        System.out.println(threeSumClosest(array, 3));
    }

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int result = target;
        int min = Integer.MAX_VALUE;
        out: for (int i = 0; i < nums.length; i++) {
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == target) {
                    result = target;
                    break out;
                }
                int diff = Math.abs(sum - target);
                if (diff < min) {
                    min = diff;
                    result = sum;
                }
                if (sum > target) {
                    r--;
                } else {
                    l++;
                }
            }
        }
        return result;
    }
}
