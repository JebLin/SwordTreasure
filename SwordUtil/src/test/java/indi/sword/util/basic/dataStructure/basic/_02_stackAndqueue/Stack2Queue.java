package indi.sword.util.basic.dataStructure.basic._02_stackAndqueue;

import java.util.Stack;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 20:27 2018/2/6
 * @Modified By
 */
/*
    给你两个 stack ，实现 queue
 */
public class Stack2Queue {

    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    /**
     * @Description 每次入栈的时候，先把 stack2 的倒到 stack1 里面去，然后在对 stack1 入栈，然后继续倒进去 stack2 
     * @Author rd_jianbin_lin
     * @Date 20:45 2018/2/6
     * @Modified By
     */
    public void push(Integer node){
        Integer temp = null;
        while(!stack2.empty() && null != (temp = stack2.pop())){
            stack1.push(temp);
        }
        stack1.push(node);

        while(!stack1.empty() && null != (temp = stack1.pop())){
            stack2.push(temp);
        }
    }

    public Integer pop(){
        return stack2.empty() ? null : stack2.pop();
    }

    public static void main(String[] args) {
        Stack2Queue queue = new Stack2Queue();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        System.out.println("--- " + queue.pop());
        queue.push(5);
        queue.push(6);

        Integer temp = null;
        while (null != (temp = queue.pop())){
            System.out.println(temp);
        }


    }

}
