package com.hikvision.letcode;

import java.util.Stack;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
 * @date 2021/9/22 19:24
 */
public class Demo25 {
    public static void main(String[] args) {
        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        System.out.println(reverseKGroup(node, 3));
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        Stack<ListNode> stack = new Stack<>();
        ListNode curent = null;
        ListNode result = null;
        int index = 1;
        while (head != null) {
            stack.push(new ListNode(head.val, null));
            if (index % k == 0) {
                if (index == k) {
                    curent = stack.pop();
                    result = curent;
                }
                while (curent != null && !stack.isEmpty()) {
                    curent.next = stack.pop();
                    curent = curent.next;
                }
            }
            ++index;
            head = head.next;
        }
        if (!stack.isEmpty()) {
            for (int i = 0; i < stack.size(); i++) {
                if (curent == null) {
                    curent = stack.elementAt(i);
                    result = curent;
                } else {
                    curent.next = stack.elementAt(i);
                    curent = curent.next;
                }
            }
        }
        return result;
    }

    static class ListNode {
        private int val;
        private ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }
}
