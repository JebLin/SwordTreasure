package indi.sword.util.basic._other;

import java.util.Stack;
import java.util.Vector;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 12:01 AM 29/03/2018
 * @MODIFIED BY
 */
/*
     题目1511：从尾到头打印链表 时间限制：1 秒 内存限制：128 兆 特殊判题：否 提交：1082 解决：350 题目描述： 输入一个链表，从尾到头打印链表每个节点的值。
 */
public class TestPrintListInversely {

    public static void main(String[] args) {
        // node6 -> node5 -> node4 -> node3 -> node2 -> node1 -> null
        ListNode node1 = new ListNode(1,null);
        ListNode node2 = new ListNode(2,node1);
        ListNode node3 = new ListNode(3,node2);
        ListNode node4 = new ListNode(4,node3);
        ListNode node5 = new ListNode(5,node4);
        ListNode node6 = new ListNode(6,node5);

        // node1 -> node2 -> node3 -> node4 -> node5 -> node6
        printListInverselyUsingStack(node6);
        System.out.println("---");
        printListInverselyUsingVector(node6);
        System.out.println("---");
        printListInverselyUsingRecursion(node6);

    }
    /**
     * @Description
     *      使用栈的方式进行
     * @Author jeb_lin
     * @Date 12:18 AM 29/03/2018
     * @MODIFIED BY
     */
    public static void printListInverselyUsingStack(ListNode root) {
        Stack<ListNode> stack = new Stack<>();
        ListNode current = root;
        while(null != current){
            stack.push(current);
            current = current.next;
        }

        while(!stack.empty()){
            System.out.println(stack.pop());
        }
    }

    /**
     * @Description
     *      使用vector
     * @Author jeb_lin
     * @Date 12:18 AM 29/03/2018
     * @MODIFIED BY
     */
    public static void printListInverselyUsingVector(ListNode root) {
        Vector<ListNode> vector = new Vector<>();
        ListNode current = root;
        while(null != current){
            vector.insertElementAt(current,0); // insert in the position index 0
            current = current.next;
        }
        vector.forEach(System.out::println);
    }
    /**
     * @Description
     *      使用 递归 recursion
     * @Author jeb_lin
     * @Date 12:25 AM 29/03/2018
     * @MODIFIED BY
     */
    public static void printListInverselyUsingRecursion(ListNode current) {

        if(null != current.next){
            printListInverselyUsingRecursion(current.next);
        }
        System.out.println(current);
    }

    private static class ListNode {
        int val; // 节点的值
        ListNode next; // 子节点

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val + "}";
        }
    }
}

