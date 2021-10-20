package com.hikvision.other.test;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhangwei151
 * @description Test1
 * @date 2021/7/31 22:06
 */
public class Test1 {

    @Test
    public void testSortOne() {

        int[] nums = {5,2,7,3,4,1,5,10,11,43,23};
        final int length = nums.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (nums[i] > nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }

        Arrays.stream(nums).forEach(System.out::println);
    }
}
