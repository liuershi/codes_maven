package com.hikvision.letcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/two-sum/
 * @date 2021/7/21 20:29
 */
public class Demo1 {
    public static void main(String[] args) {
        int[] params = {2, 34, 23, 11, 45};
        int target = 45;
        Arrays.stream(twoSum(params, target)).forEach(System.out::println);
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }
}
