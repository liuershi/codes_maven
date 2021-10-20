package com.hikvision.letcode;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 * @date 2021/9/22 16:17
 */
public class Demo24 {
    public static void main(String[] args) {

    }

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        return recursiveSwap(head, head.next);
    }

    public ListNode recursiveSwap(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode next = l2.next;
        ListNode nextNext = next == null ? null : next.next;
        l2.next = l1;
        l1.next = recursiveSwap(next, nextNext);
        return l2;
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
}
