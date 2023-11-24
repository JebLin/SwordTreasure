package indi.sword.util.basic.dataStructure.basic._06_Hash.Linked;

import indi.sword.util.basic.dataStructure.basic._06_Hash.Info;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 16:16 2018/2/7
 * @Modified By
 */
// 链表
public class MyLinkList {

    // 头结点
    private Node first;

    public MyLinkList() {
        this.first = null;
    }

    // 头插法
    public void insertFirst(Info info){
        Node node = new Node(info);
        node.next = first;
        first = node;
    }

    // 头删法
    public Node deleteFirst(){
        Node temp = first;
        first = first.next;
        return temp;
    }

    // 查找方法
    public Node find(String key){
        Node current = first;
        while(!key.equals(current.getInfo().getKey())){
            if(current.next == null){
                return null;
            }
            current = current.next;
        }
        return current;
    }

    // 删除方法
    public Node delete(String key){
        Node previous = first;
        Node current = first;
        if(key.equals(first.getInfo().getKey())){
            first = first.next;
            return current;
        }else{
            while(!key.equals(current.getInfo().getKey())){
                previous = current;
                if(current.next == null){
                    return null;
                }else {
                    current = current.next;
                }
            }
            previous.next = current.next;
            return current;
        }
    }

    // 输出
    public void print(){
        Node current = first;
        while(null != current ){
            System.out.println(current.info);
            current = current.next;
        }
    }

}
