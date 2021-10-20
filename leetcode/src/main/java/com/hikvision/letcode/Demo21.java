package com.hikvision.letcode;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/merge-two-sorted-lists
 * @date 2021/9/22 10:06
 */
public class Demo21 {
    public static void main(String[] args) {
        System.out.println();
    }

    /**
     * 方式二：采用递归的方式
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoListsTwo(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoListsTwo(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoListsTwo(l2.next, l1);
            return l2;
        }
    }

    /**
     * 方式一：循环比较两个链表的节点大小，最大时间复杂度为O(m+n)
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode result = null;
        ListNode median = null;
        int index = 0;
        while (true) {
            ListNode current;
            if (l1 == null || l2 == null) {
                median.next = l1 == null ? l2 : l1;
                break;
            }
            if (l1.val <= l2.val) {
                current = l1;
                l1 = l1.next;
            } else {
                current = l2;
                l2 = l2.next;
            }
            if (index == 0) {
                median = new ListNode(current.val, null);
                result = median;
            } else {
                median.next = new ListNode(current.val, null);
                median = median.next;
            }
            ++index;
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
    }
}
