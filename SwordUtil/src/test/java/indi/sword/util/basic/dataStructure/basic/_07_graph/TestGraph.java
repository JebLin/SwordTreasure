package indi.sword.util.basic.dataStructure.basic._07_graph;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 17:17 2018/2/7
 * @Modified By
 */
public class TestGraph {
    public static void main(String[] args) {

        Graph graph = new Graph();
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addVertex('F');
        graph.addVertex('G');
        graph.addVertex('H');
        graph.addVertex('Y');
        graph.addVertex('K');
        graph.addVertex('I');

        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 5);
        graph.addEdge(3, 4);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 9);
        graph.addEdge(8, 10);

//        graph.dfs();
        graph.bft();
    }

}
