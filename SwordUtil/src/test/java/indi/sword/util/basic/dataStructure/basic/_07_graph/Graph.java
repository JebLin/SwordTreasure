package indi.sword.util.basic.dataStructure.basic._07_graph;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 17:08 2018/2/7
 * @Modified By
 */
// 图
public class Graph {

    // 顶点数组
    private Vertex[] vertexList;

    // 邻接矩阵
    private int[][] adjMat;

    // 顶点的最大数目
    private int maxSize = 20;

    // 当前顶点
    private int nVertex;

    // 栈，负责深度优先算法
    private Stack<Integer> stack;

    // 队列，负责广度优先算法
    private Queue<Integer> queue;

    public Graph(){
        vertexList = new Vertex[maxSize];
        adjMat = new int[maxSize][maxSize];
        // 初始化邻接关系
        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < maxSize; j++) {
                adjMat[i][j] = 0;
            }
        }
        nVertex = 0;
        stack = new Stack();
        queue = new ArrayBlockingQueue(maxSize);
    }

    // 添加顶点
    public void addVertex(char label){
        vertexList[nVertex++] = new Vertex(label);
    }

    // 添加边
    public void addEdge(int start,int end){
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }


    /**
     * @Description depth 深度优先遍历
     * @Author rd_jianbin_lin
     * @Date 18:03 2018/2/7
     * @Modified By
     */
    /*
        深度优先遍历: 思路就是从根节点开始，一个个往stack压，
        然后找邻接点有没有被访问过，没有的话就继续压栈，如果邻接点全部被访问过了，
        那么就pop出去，直到最后把根节点给pop出去，遍历结束。

        1、如果可能，访问一个邻接点的未访问过的顶点。标记它，并把它放入栈中。
        2、当不能执行规则 1 时，如果栈不为空，那么就从栈中弹出一个顶点。
        3、当不能执行规则 1 和 规则 2时，就完成了整个搜索过程

        深度优先： 1打印，2邻接点入栈，打印 ，3找不到邻接点了，出栈出栈再找邻接点
     */
    public void dfs(){
        // 首先访问 0 号顶点
        vertexList[0].setVisited(true);
        // 打印该顶点
        displayVertex(0);

        stack.push(0);
        while(!stack.isEmpty()){
            // 找到未访问的邻接点
            int v = getadjUnvisitedVertex((int)stack.peek());
            if(v == -1){
                // 弹出一个顶点
                stack.pop();
            }else{
                displayVertex(v);
                vertexList[v].setVisited(true);
                stack.push(v);
            }
        }

        // 搜索完毕之后，复原状态
        for (int i = 0; i < nVertex; i++) {
            vertexList[i].setVisited(false);
        }
    }

    /**
     * @Description breadth-first 广度优先算法
     * @Author rd_jianbin_lin
     * @Date 19:25 2018/2/7
     * @Modified By
     */
    /*
        一行一行输出
        规则： 出队列的时候打印

        1、访问下一个邻接的未访问过的顶点，这个顶点必须是当前节点的邻接点，标记它，并把它查到队列中。
        2、如果无法执行规则 1 ，那么就从队列中取出一个顶点，并使其作为当前顶点。
        3、当队列为空不能执行规则 2是，就完成了整个搜索过程。

        eg.
        1、A 入队列
        2、加邻接点进来
        3、出队列，顺便打印

     */
    public void bft() {
        // 首先访问 0 号顶点
        vertexList[0].setVisited(true);
        // 1、标记完之后，要入队列
        queue.offer(0);

        int currentVertex = 0;
        while(!queue.isEmpty()){
            currentVertex = queue.peek();
//            System.out.println("current -> " + vertexList[currentVertex]);
            // 1、找到未访问的邻接点，入队列
            int v;
            while(-1 != (v = getadjUnvisitedVertex(currentVertex))){
                // 邻接点入队列
                queue.offer(v);
//                System.out.print("---入queue---");
//                System.out.print(vertexList[v].getLabel() + "-");
                vertexList[v].setVisited(true);
            }
            // 2、出队列
            queue.poll();
            // 3、出队列的时候打印
            displayVertex(currentVertex);
        }




        //搜索完以后，要将访问信息修改
        for(int i = 0; i < nVertex; i++) {
            vertexList[0].setVisited(false);
        }
    }


    /**
     * @Description 找到未访问的邻接点
     * @Author rd_jianbin_lin
     * @Date 18:23 2018/2/7
     * @Modified By
     */
    private int getadjUnvisitedVertex(int v) {
        for (int i = 0; i < vertexList.length; i++) {
            if(adjMat[v][i] == 1 && vertexList[i].isVisited() == false){
                return i;
            }
        }
        return -1;
    }

    // 打印
    private void displayVertex(int i) {
        System.out.println("---出queue---" + vertexList[i].getLabel());
    }


}
