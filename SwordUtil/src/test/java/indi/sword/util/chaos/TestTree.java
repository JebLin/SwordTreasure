package indi.sword.util.chaos;

import com.alibaba.fastjson.JSON;
import lombok.ToString;

import java.beans.IntrospectionException;
import java.util.*;

/**
 * @author jeb_lin
 * 22:48 2023/11/15
 */
public class TestTree {
    @ToString
    public static class Node {
        public Node left;
        public Node right;
        public int value;

        public Node(int value) {
            this.value = value;

        }
    }


    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 4, 5, 6};
//        Node head = getTreeFromArr(arr);
        Node head2 = getTreeFromArr2(arr);
//        System.out.println(JSON.toJSONString(head));
//        System.out.println(JSON.toJSONString(head2));

        /*
            前序遍历：[0, 1, 3, 4, 2, 5, 6]
            中序遍历：[3, 1, 4, 0, 5, 2, 6]
            后序遍历：[3, 4, 1, 5, 6, 2, 0]
         */

        System.out.println("preorderPrint ->");
        preorderPrint(head2);
        System.out.println();

        System.out.println("inorderPrint ->");
        inorderPrint(head2);
        System.out.println();

        System.out.println("postorderPrint ->");
        postorderPrint(head2);
        System.out.println();
        System.out.println("——————————");


        preorderPrintByStack(head2);
        inOrderPrintByStack(head2);
        postorderPrintByStack(head2);

        System.out.println();
        widthPrint(head2);

        System.out.println("----------");
//        Node specialTree = getSpecialTree();
//        widthPrint(specialTree);
//
//        getTreeWidth(specialTree);


        Node bstTreeHeadNode = getBSTTree();
//        boolean isBST = checkBSTHelperWithStack(bstTreeHeadNode);
//        System.out.println(isBST);
//
//        printRight2Left(bstTreeHeadNode);

        Node normalTree = getNormalTree();
        bfsPrint(normalTree);
        System.out.println("dfsPrint ===");
        dfsPrint(normalTree);
        System.out.println();
        System.out.println("====");
        dfsPrintByStack(normalTree);
        System.out.println();
        depthFirstSearch(normalTree);


        Integer[] nums = {1, 2, 2, 3, null, null, 5, 4, null, null, 4};
        Node head = getTreeFromArr(nums);
        System.out.println(head);
        int depth = calculateDepth(getSpecialTree());
        System.out.println();
        System.out.println(depth);

        ListNode[] listOfDepth = listOfDepth(getSpecialTree());
        System.out.println(listOfDepth);

    }

    @ToString
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode[] listOfDepth(Node head) {
        List<ListNode> result = new ArrayList<>();

        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            ListNode listNode = null;

            for (int i = 0; i < queueSize; i++) {
                Node temp = queue.poll();
                if(i == 0){
                    listNode = new ListNode(temp.value);
                    result.add(listNode);
                } else {
                    listNode.next = new ListNode(temp.value);
                    listNode = listNode.next;
                }
//                System.out.print(temp.value + ", ");
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }
        }
        ListNode[] arr = new ListNode[result.size()];
        return result.toArray(arr);

    }

    private static Node getTreeFromArr(Integer[] nums) {
        if (null == nums || nums.length == 0) {
            return null;
        }
        Node head = new Node(nums[0]);
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(head);

        int index = 1;
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            if (index < nums.length) {
                if (null != nums[index]) {
                    temp.left = new Node(nums[index]);
                    queue.offer(temp.left);
                }
                index++;
            }
            if (index < nums.length) {
                if (null != nums[index]) {
                    temp.right = new Node(nums[index]);
                    queue.offer(temp.right);
                }
                index++;
            }
        }
        return head;
    }


    /**
     * DFS思想，深度优先检索思想
     */
    public static int calculateDepth(Node temp) {
        if (null == temp) {
            return 0;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(temp);
        // 记录每个Node对应的层级
        Map<Node, Integer> node2DepthMap = new HashMap<>();
        node2DepthMap.put(temp, 1);
        while (!stack.isEmpty()) {
            temp = stack.pop();
            int level = node2DepthMap.get(temp);
//            System.out.print(temp.value + ", ");
            if (temp.right != null) {
                stack.push(temp.right); // 先压右
                node2DepthMap.put(temp.right, level + 1);
            }
            if (temp.left != null) {
                stack.push(temp.left); // 再压左
                node2DepthMap.put(temp.left, level + 1);
            }
        }
        return Collections.max(node2DepthMap.values());
    }

    /**
     * depth First Search
     */
    private static void dfsPrint(Node head) {
        if (null == head) {
            return;
        }
        System.out.print(head.value + ", ");
        dfsPrint(head.left);
        dfsPrint(head.right);
    }

    /**
     * depth First Search
     */
    private static void dfsPrintByStack(Node head) {
        if (null == head) {
            return;
        }
        System.out.println("dfsPrintByStack === ");
        Node temp = head;
        Stack<Node> stack = new Stack<>();
        stack.push(temp);
        while (!stack.isEmpty()) {
            temp = stack.pop();
            System.out.print(temp.value + ", ");
            if (temp.right != null) {
                stack.push(temp.right);// 先压入右子树
            }
            if (temp.left != null) {
                stack.push(temp.left);// 再压入左子树，确保左子树先出栈
            }
        }
    }

    public static void depthFirstSearch(Node root) {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            System.out.print(current.value + " ");

            if (current.right != null) {
                stack.push(current.right); // 先压入右子树
            }
            if (current.left != null) {
                stack.push(current.left); // 再压入左子树，确保左子树先出栈
            }
        }
    }

    /**
     * breadth First Search
     */
    private static void bfsPrint(Node head) {
        if (null == head) {
            return;
        }
        System.out.println("bfsPrint ===");
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                Node temp = queue.poll();
                System.out.print(temp.value + ", ");
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }
        }
    }

    private static void printRight2Left(Node head) {
        System.out.println("=== printRight2Left ===");
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(head);

        while (!queue.isEmpty()) {
            List<Node> levelNodeList = new LinkedList<>();
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Node temp = queue.poll();
                // 单纯的打印就是前序遍历
//                System.out.print(temp.value + ", ");
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
                levelNodeList.add(temp);
            }
            System.out.println(levelNodeList.get(levelNodeList.size() - 1).value);
        }
    }

    // 说下为什么不用全局变量，这玩意线程不安全
    private static int preValue = Integer.MIN_VALUE;

    private static class Wrapper {
        int value;

        Wrapper(int value) {
            this.value = value;
        }
    }

//    private static boolean checkBST(Node head) {
//        return checkBSTHelperWithStack(head, new Wrapper(Integer.MIN_VALUE));
//    }

    /**
     * 1、如何判断一棵树是不是搜索二叉树？
     * 思路：左中右，那么就是中序遍历
     */
    private static boolean checkBSTHelper(Node head, Wrapper preValue) {
        if (null == head) {
            return true;
        }
        // 判断左边是不是 BST
        if (!checkBSTHelper(head.left, preValue)) {
            return false;
        }

//        System.out.println(head.value); // 这句如果是打印，那么就是 前序遍历输出
        // 判断当前
        if (preValue.value > head.value) {
            return false;
        }

        // 第一轮，到最左下角的时候，会把值赋给 preValue，作为有意义的第一个值（因为本来中序遍历的逻辑是print这个value）
        preValue.value = head.value;

        // 判断右边是不是 BST
        return checkBSTHelper(head.right, preValue);
    }

    /**
     * 1、如何判断一棵树是不是搜索二叉树？
     * 思路：左中右，那么就是中序遍历
     * Stack方法
     */
    private static boolean checkBSTHelperWithStack(Node head) {
        if (null == head) {
            return true;
        }
        System.out.println(" === checkBSTHelperWithStack ===");
        Node current = head;

        Stack<Node> stack = new Stack<>();
        stack.push(current);

        int preValue = Integer.MIN_VALUE;
        while (!stack.isEmpty()) {
            while (current != null && current.left != null) {
                stack.push(current.left);
                current = current.left;
            }
            current = stack.pop();
            if (preValue > current.value) {
                return false;
            } else {
                preValue = current.value;
            }

            System.out.print(current.value + ", ");

            if (current.right != null) {
                stack.push(current.right);
            }
            current = current.right;

        }
        return true;
    }


    private static void getTreeWidth(Node root) {
        System.out.println();
        System.out.println("---getTreeWidth---");
        Node temp = root;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(temp);

        int maxLevelSize = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            maxLevelSize = Math.max(maxLevelSize, levelSize);
            // 思路就是：for完结束之后，才会进入下一层
            for (int i = 0; i < levelSize; i++) {
                temp = queue.poll();
                System.out.print(temp.value + ",");
                if (temp.left != null) {
                    queue.offer(temp.left);

                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }
        }
        System.out.println("maxLevelSize -> " + maxLevelSize);
    }

    /**
     * 前序遍历（中左右）
     */
    public static void preorderPrint(Node temp) {
        if (null == temp) {
            return;
        }
        System.out.print(temp.value + ",");
        preorderPrint(temp.left);
        preorderPrint(temp.right);
    }

    /**
     * 中序遍历（左中右）
     */
    public static void inorderPrint(Node temp) {
        if (null == temp) {
            return;
        }
        inorderPrint(temp.left);
        System.out.print(temp.value + ",");
        inorderPrint(temp.right);
    }

    /**
     * 右序遍历（左中右）
     */
    public static void postorderPrint(Node temp) {
        if (null == temp) {
            return;
        }
        postorderPrint(temp.left);
        postorderPrint(temp.right);
        System.out.print(temp.value + ",");
    }

    /**
     * Stack方法
     * 前序遍历（中左右）
     */
    public static void preorderPrintByStack(Node root) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        // 可以不用queue，可以直接打印，省空间 N
        // 我加一个 queue的目的是为了后序的翻转好理解
        Queue<Node> queue = new LinkedList<>();

        while (!stack.isEmpty()) {
            Node temp = stack.pop();
//            System.out.print(temp.value + ",");
            // 此处可以不打印，直接入一个新的 queue
            queue.offer(temp);
            if (temp.right != null) {
                stack.push(temp.right);
            }
            if (temp.left != null) {
                stack.push(temp.left);
            }
        }
        System.out.print("preorderPrintByStack -> ");
        while (!queue.isEmpty()) {
            System.out.print(queue.poll().value + ",");
        }
        System.out.println();
    }

    /**
     * Stack方法
     * 中序遍历（左中右）
     */
    public static void inOrderPrintByStack(Node root) {
        if (null == root) {
            return;
        }
        Node current = root;
        Stack<Node> stack = new Stack<>();

        // 可以不用queue，可以直接打印，省空间 N
        // 我加一个 queue的目的是为了后序的翻转好理解
        Queue<Node> queue = new LinkedList<>();

        while (current != null || !stack.isEmpty()) {
            // 如果current不是null，那么就一直往左找下去
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
//            System.out.print(current.value + ",");
            queue.offer(current);
            // 左边已经干完了，那么就一直往右找
            current = current.right;
        }

        System.out.print("inOrderPrintByStack -> ");
        while (!queue.isEmpty()) {
            System.out.print(queue.poll().value + ",");
        }
    }


    /**
     * 右序遍历（左右中）
     * 翻过来就是 中右左， 因为前序是中左右，前序是先压right再压left，那么反过来，先压left再压right的话，就会是中右左了
     */
    public static void postorderPrintByStack(Node temp) {
        if (null == temp) {
            return;
        }
        Stack<Node> stackA = new Stack<>();
        stackA.push(temp);

        // 注意和之前的区别，上面的中序和前序都是 queue ，FIFO，那么 Stack 的FILO，就能实现这个翻转
        Stack<Node> stackB = new Stack<>();

        while (!stackA.isEmpty()) {
            temp = stackA.pop();
            stackB.push(temp);
            if (temp.left != null) {
                stackA.push(temp.left);
            }
            if (temp.right != null) {
                stackA.push(temp.right);
            }
        }

        System.out.print("postorderPrintByStack -> ");
        while (!stackB.isEmpty()) {
            System.out.print(stackB.pop().value + ",");
        }
    }

    /**
     * 宽度遍历，其实就是中左右，也就是前序遍历
     * 用一个 queue 辅助也是可以搞定的
     *
     * @param head
     */
    public static void widthPrint(Node head) {
        if (null == head) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);

        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.print(temp.value + ",");
            if (temp.left != null) {
                queue.offer(temp.left);
            }
            if (temp.right != null) {
                queue.offer(temp.right);
            }
        }
    }


    public static Node getTreeFromArr2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        LinkedList<Node> quque = new LinkedList<>();
        Node root = new Node(arr[0]);

        quque.add(root);
        int index = 1;
        while (!quque.isEmpty()) {
            // pop 0的时候，刚好操作1、2
            // pop 1的时候，刚好操作3、4
            // pop 2的时候，刚好操作5、6
            Node temp = quque.pop();
            if (index < arr.length) {
                temp.left = new Node(arr[index]);
                quque.add(temp.left);
                index++;
            }

            if (index < arr.length) {
                temp.right = new Node(arr[index]);
                quque.add(temp.right);
                index++;
            }
        }
        return root;
    }


    public static Node getSpecialTree() {
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);
        Node node10 = new Node(10);
        Node node11 = new Node(11);

        node0.left = node1;
        node0.right = node2;

        node1.left = node3;
        node1.right = node4;

        node2.left = node5;

        node3.left = node6;

        node4.left = node7;
        node4.right = node8;

        node5.right = node9;

        node7.left = node10;
        node7.right = node11;

        return node0;
    }

    /**
     * 构造一棵二叉树
     */
    public static Node getNormalTree() {
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        node0.left = node1;
        node0.right = node2;

        node1.left = node3;
        node1.right = node4;

        node2.left = node5;
        node2.right = node6;
        return node0;
    }

    /**
     * 构造一棵二叉树
     */
    public static Node getBSTTree() {
        Node node5 = new Node(5);
        Node node4 = new Node(4);
        Node node7 = new Node(7);
        Node node2 = new Node(2);
        Node node6 = new Node(6);
        Node node8 = new Node(8);
        Node node1 = new Node(1);
        Node node3 = new Node(3);

        node5.left = node4;
        node5.right = node7;

        node4.left = node2;
        node4.right = null;

        node2.left = node1;
        node2.right = node3;

        node7.left = node6;
        node7.right = node8;

        return node5;
    }


}
