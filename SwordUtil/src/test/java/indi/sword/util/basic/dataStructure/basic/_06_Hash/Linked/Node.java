package indi.sword.util.basic.dataStructure.basic._06_Hash.Linked;

import indi.sword.util.basic.dataStructure.basic._06_Hash.Info;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 16:17 2018/2/7
 * @Modified By
 */
/*
    链节点，相当于车厢
 */
public class Node {
    // 数据域
    public Info info;

    // 指针域
    public Node next;

    public Node(Info info) {
        this.info = info;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "info=" + info +
                '}';
    }
}
