package com.hikvision.other.test;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhangwei151
 * @description Test2
 * @date 2021/8/1 21:43
 */
public class Test2 {

    /**
     * 演示冒泡排序
     * @param array 待排序数组
     */
    public void bubbleSort(int[] array) {
        final int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    @Test
    public void test() {
        int[] array = {10,33,23,54,1,3,5,57,23,87,0,32};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
    }
}
