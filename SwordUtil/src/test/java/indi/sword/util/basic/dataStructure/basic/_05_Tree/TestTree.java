package indi.sword.util.basic.dataStructure.basic._05_Tree;

public class TestTree {
    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.insert(10,"a10");
        tree.insert(5,"a5");
        tree.insert(12,"a12");
        tree.insert(3,"a3");
        tree.insert(8,"a8");
        tree.insert(11,"a11");
        tree.insert(15,"a15");
        tree.insert(1,"a1");
        tree.insert(4,"a4");
        tree.insert(7,"a7");
        tree.insert(9,"a9");

        tree.centralOrder(tree.root);
        System.out.println();
        tree.afterOrder(tree.root);
    }
}
