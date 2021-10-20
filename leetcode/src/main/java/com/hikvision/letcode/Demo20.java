package com.hikvision.letcode;

import java.util.Stack;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/valid-parentheses
 * @date 2021/9/21 21:39
 */
public class Demo20 {
    public static void main(String[] args) {
        System.out.println(isValid("((("));
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || c!=stack.pop()) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
