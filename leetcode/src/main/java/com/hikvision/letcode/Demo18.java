package com.hikvision.letcode;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/4sum/
 * @date 2021/7/27 16:57
 */
public class Demo18 {
    public static void main(String[] args) {
        int[] array = {-3,-2,-1,0,0,1,2,3};
        System.out.println(fourSum(array, 0));
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        final int length = nums.length;
        if (length < 4) {
            return result;
        }
        Arrays.sort(nums);
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                int l = j + 1, r = length - 1;
                while (l < r) {
                    int sum = nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum == target) {
                        final List<Integer> item = Arrays.asList(nums[i], nums[j], nums[l], nums[r]);
                        final List<String> checkList = result.stream().map(list -> list.stream().map(String::valueOf).collect(Collectors.joining())).collect(Collectors.toList());
                        if (!checkList.contains(item.stream().map(String::valueOf).collect(Collectors.joining()))) {
                            result.add(item);
                        }
                    }
                    if (sum > target) {
                        --r;
                    } else if (target > sum){
                        ++l;
                    } else {
                        --r;
                        ++l;
                    }
                }
            }
        }
        return result;
    }
}
