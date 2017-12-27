package indi.sword.util.basic.dataStructure.basic._05_Tree;

/**
 * 二叉树类
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/27 19:29
 */
public class Tree {
    // 根节点
    public Node root;

    public void insert(long data,String value){
        // 新插入的节点
        Node newNode = new Node(data,value);
        // 当前节点
        Node current = root;
        if(null == current){
            root = newNode;
        }else {
            Node parent;
            while (true){
                // 父节点指向当前节点
                parent = current;
                // 如果新节点比当前节点大，走左边
                if (data > current.data){
                    current = current.rightChild;
                    if(null == current){
                        parent.rightChild = newNode;
                        return ;
                    }
                }else{
                    current = current.leftChild;
                    if(null == current){
                        parent.leftChild = newNode;
                        return ;
                    }
                }
            }
        }
    }

    /**
     * 查找节点
     * @param value
     */
    public Node find(long value){
        //引用当前节点，从根节点开始
        Node current = root;
        // 查找值跟当前节点不相等且不为空，进入循环
        while (current != null && current.data != value){
            //进行比较，比较查找值和当前节点的大小
            if(value > current.data){
                current = current.rightChild;
            }else{
                current = current.leftChild;
            }
        }
        return current;
    }

    /**
     * 删除节点四种情况：要删除的节点的子节点情况：
     * 1.叶子节点 2、有一个左子节点 3、有一个右子节点 4、有两个节点
     * @Decription
     * @Author: rd_jianbin_lin
     * @Date : 2017/12/27 20:06
     */
    public void delete(long value){
        Node current = root;
        if(null == current){
            return ;
        }else{
            Node parent = current ;
            boolean isLeftChild = false;
            // 查找值跟当前节点不相等且不为空，进入循环
            while (current != null && current.data != value){
                parent = current;
                //进行比较，比较查找值和当前节点的大小
                if(value > current.data){
                    current = current.rightChild;
                    isLeftChild = false;
                }else{
                    current = current.leftChild;
                    isLeftChild = true;
                }
            }
            // 没找到
            if(null == current){
                return ;
            }else{
                // 属于叶子节点
                if(null == current.leftChild && null == current.rightChild ){
                    if(isLeftChild){
                        parent.leftChild = null;
                    }else {
                        parent.rightChild = null;
                    }
                } else if(null == current.leftChild){ // 如果有右孩子
                    if(isLeftChild){
                        parent.leftChild = current.rightChild;
                    }else {
                        parent.rightChild = current.rightChild;
                    }
                } else if(null == current.rightChild){ // 如果有左孩子
                    if(isLeftChild){
                        parent.leftChild = current.leftChild;
                    }else {
                        parent.rightChild = current.leftChild;
                    }
                } else{
                    /*
                        就是有两个孩子的话，那么最复杂。思路就是把最左子树给拉上去，这个你画图就知道了
                        那么最左子树有两种情况：
                            1.这个最左子树是叶子节点
                            2.这个最左子树有右孩子（不可能有左孩子，不然这个左孩子就可以上位了。）
                        针对上述两种情况：
                            第一种情况，就把最左叶子节点替换到删除的节点那里去，注意安顿好删除节点的孩子
                            第二种情况，就把右孩子替换到要删除的节点那里去
                     */
                    Node leftest = current.leftChild;
                    Node leftestParent = leftest;// 最左节点的爸爸
                    while(null != leftest.leftChild){ // 它的左孩子为空话，那它就是最左节点
                        leftestParent = leftest;
                        leftest = leftest.leftChild;
                    }
                    leftestParent.leftChild = null; // 节点移走了，那么原先指向它的要指向空
                    if(null == leftest.rightChild){ // 这是上面的第一种情况
                        if(isLeftChild){
                            parent.leftChild = leftest;
                        }else{
                            parent.rightChild = leftest;
                        }
                        leftest.leftChild = current.leftChild;
                        leftest.rightChild = current.rightChild;
                    }else{ // 这是上面的第二种情况
                        if(isLeftChild){
                            parent.leftChild = leftest.rightChild; // 接上去
                            leftest.rightChild.leftChild = leftestParent; // 接上去后，接手的爸爸
                            leftestParent.rightChild = null; // 爸爸的右孩子 = null
                            leftest.rightChild.rightChild = current.rightChild; // 接上去后，接手current的右孩子
                        }else{
                            parent.rightChild = leftest.rightChild;
                            leftest.rightChild.leftChild = leftestParent;
                            leftestParent.rightChild = null;  // 爸爸的右孩子 = null
                            leftest.rightChild = current.rightChild;

                        }
                    }

                }



            }



        }


    }

    /**
     * @Decription 前序排序     中左右
     * @Author: rd_jianbin_lin
     * @Date : 2017/12/27 20:11
     */
    public void frontOrder(Node localNode){
        if (null == localNode){
            return ;
        }
        System.out.print(localNode + ",");
        frontOrder(localNode.leftChild);
        frontOrder(localNode.rightChild);
    }

    /**
     * @Decription 中序排序     左中右
     * @Author: rd_jianbin_lin
     * @Date : 2017/12/27 20:11
     */
    public void centralOrder(Node localNode){
        if(null == localNode){
            return;
        }
        centralOrder(localNode.leftChild);
        System.out.print(localNode + ",");
        centralOrder(localNode.rightChild);
    }

    /**
     * @Decription 后序排序     左右中
     * @Author: rd_jianbin_lin
     * @Date : 2017/12/27 20:11
     */
    public void afterOrder(Node localNode){
        if(null == localNode){
            return;
        }
        afterOrder(localNode.leftChild);
        afterOrder(localNode.rightChild);
        System.out.print(localNode + ",");
    }
}
