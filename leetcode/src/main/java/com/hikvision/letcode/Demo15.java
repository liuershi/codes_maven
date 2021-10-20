package com.hikvision.letcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/3sum/
 * @date 2021/7/26 15:08
 */
public class Demo15 {
    public static void main(String[] args) {
        int[] array = {-1,0,1,2,-1,-4,-2,-3,3,0,4};
        System.out.println(threeSum(array));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length < 3) {
            return result;
        }
        final int length = nums.length;
        Arrays.sort(nums);
        for (int i = 0; i < length; i++) {
            int l = i + 1, r = length - 1;
            if (nums[i] > 0) {
                return result;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            while (l < r) {
                if (nums[i] + nums[l] + nums[r] == 0) {
                    final List<Integer> add = Arrays.asList(nums[i], nums[l], nums[r]);
                    result.add(add);
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    l++;
                    r--;
                } else if (nums[i] + nums[l] + nums[r] < 0) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return result;
    }
}
