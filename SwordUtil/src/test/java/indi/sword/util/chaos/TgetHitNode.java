package indi.sword.util.chaos;

/**
 * @author jeb_lin
 * 18:01 2023/11/15
 */
public class TgetHitNode {
    public static class Node {
        private int index;
        private Node next;
    }

    public static void main(String[] args) {
        Node a1Node = new Node();
        a1Node.index = 11;

        Node a2Node = new Node();
        a2Node.index = 12;

        Node a3Node = new Node();
        a3Node.index = 13;

        a1Node.next = a2Node;
        a2Node.next = a3Node;
        a3Node.next = a2Node;
        // 11 -> 12 -> 13 -> 12


        Node b1Node = new Node();
        b1Node.index = 21;

        Node b2Node = new Node();
        b2Node.index = 22;

        b1Node.next = b2Node;
        b2Node.next = a2Node;


        /*
                   11-> 12 -> 13
                        ↗
                21 -> 22
         */
        // 找到a的入环节点，null表示没有环
        Node aLoopHitNode = getLoopHitNode(a1Node);
        Node bLoopHitNode = getLoopHitNode(b1Node);
        System.out.println("aLoopHitNode -> " + aLoopHitNode);
        if (null != aLoopHitNode) {
            System.out.println("aLoopHitNode -> " + aLoopHitNode.index);
        }
        System.out.println("bLoopHitNode -> " + bLoopHitNode);
        if (null != bLoopHitNode) {
            System.out.println("bLoopHitNode -> " + bLoopHitNode.index);
        }

        Node sameHitNode = null;
        // 情况一：a链表和b链表都无环,可能相交，也可能不相交
        if (null == bLoopHitNode && null == aLoopHitNode) {
            sameHitNode = getSameHitNodeNoLoopWithEnd(a1Node, null, b1Node, null);
        }
        // 情况二：2.1 俩链表一个有环，一个没环，肯定不相交
        if (null != bLoopHitNode && null == aLoopHitNode) {
            sameHitNode = null;
        }
        // 情况二：2.2 俩链表一个有环，一个没环，肯定不相交
        if (null == bLoopHitNode && null != aLoopHitNode) {
            sameHitNode = null;
        }

        // 情况三：俩链表都有环
        if (null != bLoopHitNode && null != aLoopHitNode) {
            // 3.1 碰撞点是同一个，说明在环前面（或者刚好环的入口）
            if (aLoopHitNode == bLoopHitNode) {
                // 为什么是 aLoopHitNode.next 与 bLoopHitNode.next？
                // 因为如果两个链表的相交点，刚好是入环点的话，那么他们终止节点应该往后挪一个，进入这个方法后，才会使得 p1 = p2
                sameHitNode = getSameHitNodeNoLoopWithEnd(a1Node, aLoopHitNode.next, b1Node, bLoopHitNode.next);
            } else {
                // 判断是否共享同一个环
                Node p1 = aLoopHitNode.next;
                while (p1 != aLoopHitNode) {
                    if (p1 == bLoopHitNode) { // 碰上了，肯定是同一个环，一定有交点
                        sameHitNode = aLoopHitNode;
                        break;
                    }
                    p1 = p1.next;
                }
                // 3.2 如果a绕了一圈，没找到b，说明不是一个环
                if(p1 == aLoopHitNode){
                    sameHitNode = null;
                }
            }
        }

        System.out.println("sameHitNode -> " + sameHitNode);

        if (null != sameHitNode) {
            System.out.println(sameHitNode.index);
        }
    }

    /**
     * 2个无环链表求交点
     *
     * @return 返回null表示没交点
     */
    private static Node getSameHitNodeNoLoopWithEnd(Node aHead, Node aEnd, Node bHead, Node bEnd) {
        if (aHead == aEnd || bHead == bEnd) {
            return null;
        }
        int aLen = 1;
        int bLen = 1;
        Node p1 = aHead;
        Node p2 = bHead;
        while (p1.next != aEnd) {
            aLen++;
            p1 = p1.next;
        }
        while (p2.next != bEnd) {
            bLen++;
            p2 = p2.next;
        }
        // 情况1： 终止节点不是一个，那肯定不相交
        if (p1 != p2) {
            return null;
        }
        // 情况2：终止节点是一个，需要找到相交点
        p1 = aHead;
        p2 = bHead;
        // 看看俩链表相差多少
        int diff = Math.abs(aLen - bLen);
        while (diff > 0) {
            if (aLen > bLen) {
                p1 = p1.next;
            } else {
                p2 = p2.next;
            }
            diff--;
        }

        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }


    /**
     * 快慢指针拿到入环节点
     */
    private static Node getLoopHitNode(Node head) {
        Node fast = head;
        Node slow = head;
        if (null == head) {
            return null;
        }
        if (fast.next == null || fast.next.next == null) {
            return null;
        }

        // 看下是否能碰撞上
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        // 说明没环，退出是因为 fast.next == null
        if (fast != slow) {
            return null;
        }
        // 有环的情况下，slow指向 head，然后slow和fast每次走一步
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
