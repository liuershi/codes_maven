package com.hikvision.letcode;

import java.util.Stack;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/longest-valid-parentheses/
 * @date 2021/9/23 20:18
 */
public class Demo32 {
    public static void main(String[] args) {
        System.out.println(longestValidParentheses("()((((()()()(()()()()"));
    }

    public static int longestValidParentheses(String s) {
        int max = 0;
        if ("".equals(s)) {
            return max;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }
}
