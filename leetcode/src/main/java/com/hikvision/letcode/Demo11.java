package com.hikvision.letcode;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/container-with-most-water
 * @date 2021/7/25 13:14
 */
public class Demo11 {
    public static void main(String[] args) {
        int[] array = {1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(array));
    }

    public static int maxArea(int[] height) {
//        return test1(height);
        return test2(height);
    }

    /**
     * 使用双指针
     * @param height
     * @return
     */
    private static int test2(int[] height) {
        int start = 0, end = height.length - 1;
        int max = 0;
        while (start < end) {
            int min = Math.min(height[start], height[end]);
            int width = end - start;
            max = Math.max(max, min * width);
            if (height[start] < height[end]) {
                ++start;
            } else {
                --end;
            }
        }
        return max;
    }

    /**
     * 暴力破解：不过时间复杂度较高，LeetCode无法通过验证，耗时较长
     * @param height
     * @return
     */
    private static int test1(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int minHeight = Math.min(height[i], height[j]);
                max = Math.max(minHeight * (j - i), max);
            }
        }
        return max;
    }
}
