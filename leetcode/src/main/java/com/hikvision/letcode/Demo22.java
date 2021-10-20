package com.hikvision.letcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/generate-parentheses
 * @date 2021/9/22 11:30
 */
public class Demo22 {
    public static void main(String[] args) {
        System.out.println(generateParenthesis(8));
    }

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        getParenthesis(result, n, n, "");
        return result;
    }

    private static void getParenthesis(List<String> result, int left, int right, String str) {
        if (left == 0 && right == 0) {
            result.add(str);
            return;
        }
        if (left == right) {
            getParenthesis(result, left - 1, right, str + "(");
        } else {
            if (left > 0) {
                getParenthesis(result, left - 1, right, str + "(");
            }
            getParenthesis(result, left, right - 1, str + ")");
        }
    }
}
