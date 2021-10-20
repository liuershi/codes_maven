package com.hikvision.letcode;

/**
 * @author zhangwei151
 * @description @see https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 * @date 2021/9/21 20:14
 */
public class Demo19 {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        System.out.println(removeNthFromEnd(listNode, 1));
    }

    /**
     * 方式二：一遍遍历（优化后）
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEndTwo(ListNode head, int n) {
        // 要点：使用该种方式，主要是同个两个指针判断哪个节点需要移除
        // 通过左指针left和右指针right，使得right - left = n,再同时向右移动，直到右指针节点的元素为null,此时左指针节点处节点应该被移除

        ListNode left = head;
        ListNode right = head;
        // 1.先让右指针走指定步数
        while (n-- > 0) right = right.next;
        // 2.若右指针为null则表示删除第一个元素，直接返回即可
        if (right == null) return left.next;
        // 3.保证左指针为应该删除节点的前一个节点，所以右移一下右节点
        right = right.next;
        while (right != null) {
            right = right.next;
            left = left.next;
        }
        left.next = left.next.next;
        return head;
    }

    /**
     * 方式一：两遍遍历
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // 1.先计算链表长度
        int length = 1;
        ListNode current = head;
        while (current.next!=null) {
            ++length;
            current = current.next;
        }
        // 2.算出需要移除的节点下标
        int index = length - n;
        // 3.再次遍历移除指定下标节点
        int i = 0;
        ListNode result = null;
        ListNode currentNode = null;
        while (head != null) {
            if (i != index) {
                if (currentNode == null) {
                    currentNode = new ListNode(head.val, null);
                    result = currentNode;
                } else {
                    currentNode.next = new ListNode(head.val, null);
                    currentNode = currentNode.next;
                }
            }
            head = head.next;
            ++i;
        }
        return result;
    }

    static class ListNode {
        private int val;
        private ListNode next;

        ListNode() {}

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
