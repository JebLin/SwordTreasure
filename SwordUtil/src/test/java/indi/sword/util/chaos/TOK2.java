package indi.sword.util.chaos;


import java.util.*;

/**
 * @author jeb_lin
 * 2:40 PM 2020/2/20
 */
public class TOK2 {

    public static class Node {
        private int index;
        private Node next;
    }

    public static void main(String[] args) {
        Node node0 = new Node();
        node0.index = 0;

        Node node1 = new Node();
        node1.index = 1;

        Node node2 = new Node();
        node2.index = 2;

        Node node3 = new Node();
        node3.index = 3;

        Node node4 = new Node();
        node4.index = 4;

        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        // 0 -> 1 -> 2 -> 3 -> 4 -> 1

//        Node hitLoopNode = checkLoopSet(node0);
        // 相遇的环形节点
        Node hitLoopNode = checkLoopFS(node0);
        if (null != hitLoopNode) {
            System.out.println("hitLoopNode -> " + hitLoopNode.index);
        } else {
            System.out.println("No");
        }

        Node loopFirstNode = null;
        // 如果无环
        if(null != hitLoopNode){
            loopFirstNode = getLoopFirstNode(node0, hitLoopNode);
            System.out.println("loopFirstNode -> " + loopFirstNode.index);
        } else {
            // 走无环的解法
        }
    }

    /**
     * @param head 头结点
     * @param hitNode 相遇点点
     * @return
     */
    private static Node getLoopFirstNode(Node head ,Node hitNode) {
        Node p1 = head;
        Node p2 = hitNode;
        while(p1 != p2){
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    /**
     * 使用快慢指针，也就是龟兔赛跑，乌龟一次走1步，兔子一次走2步，如果相遇，说明有环
     * 空间复杂度是 1
     */
    private static Node checkLoopFS(Node head) {
        Node slow = head;
        Node fast = head;
        // 极端情况 1 -> 2 -> 1
        // 如果 fast指针走完了，说明没环
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return fast;
            }
        }
        return null;
    }

    /**
     * 使用 Set， 空间复杂度是 N
     */
    private static Node checkLoopSet(Node head) {
        Set<Node> set = new HashSet<>();
        Node p1 = head;
        // 如果没有环，那么最后的节点肯定是 null
        while (p1 != null) {
            if (set.contains(p1)) {
                return p1;
            }
            set.add(p1);
            p1 = p1.next;
        }
        return null;
    }


}
