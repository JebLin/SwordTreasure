package indi.sword.util.basic.dataStructure.basic._05_Tree;

/**
 * 二叉树节点
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/27 19:24
 */
public class Node {
    // 数据源
    public long data;
    // 放多个数据在里面
    public String value;

    // 左子节点
    public Node leftChild;

    // 右子节点
    public Node rightChild;

    public Node(long data,String value){
        this.data = data;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", value='" + value + '\'' +
                '}';
    }
}
