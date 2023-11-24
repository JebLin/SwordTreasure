package indi.sword.util.chaos;


/**
 * @author jeb_lin
 * 2:40 PM 2020/2/20
 */
public class TOK {

    public static class Node {
        private int index;
        private Node next;
    }

    public static void main(String[] args) {
        Node aNode = new Node();
        aNode.index = 11;

        Node bNode = new Node();
        bNode.index = 12;

        Node cNode = new Node();
        cNode.index = 13;

        aNode.next = bNode;
        bNode.next = cNode;
        // a -> b -> c


        Node aaNode = new Node();
        aaNode.index = 21;

        Node bbNode = new Node();
        bbNode.index = 22;

        aaNode.next = bbNode;
        bbNode.next = bNode;
        // aa -> bb -> b

        /*
                     a-> b -> c
                        ↗
                aa -> bb
         */
        Node hitNode = getFirstHitNodeNoLoop(aNode, aaNode);
        System.out.println(hitNode);
        if(null != hitNode){
            System.out.println(hitNode.index);
        }
    }

    private static Node getFirstHitNodeNoLoop(Node headA, Node headB) {
        // 如果有一个链表为空，直接返回 null
        if (headA == null || headB == null) {
            return null;
        }
        // 1. 先算出俩数组长度，为什么是1呢，因为下面的退出条件是 p1.next == null,也就是会少算一个
        int lenA = 1;
        int lenB = 1;
        Node p1 = headA;
        Node p2 = headB;
        while (p1.next != null) {
            lenA++;
            p1 = p1.next;
        }

        while (p2.next != null) {
            lenB++;
            p2 = p2.next;
        }
        // 如果最后的节点不是同一个，那么肯定不相交
        if (p1 != p2) {
            return null;
        }

        p1 = headA;
        p2 = headB;

        int diff = lenB - lenA > 0 ? lenB - lenA : lenA - lenB;
        while (diff > 0) {
            if (lenA > lenB) {
                p1 = p1.next;
            } else {
                p2 = p2.next;
            }
            diff--;
        }
        // 同时移动指针，找到相交节点
        while (p1 != null && p2 != null) {
            if (p1 == p2) {
                return p1;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        return null;
    }


}
