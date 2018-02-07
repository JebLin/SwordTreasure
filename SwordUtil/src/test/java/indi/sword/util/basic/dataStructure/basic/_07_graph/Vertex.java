package indi.sword.util.basic.dataStructure.basic._07_graph;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 17:05 2018/2/7
 * @Modified By
 */
/*
    顶点类
    （在图里面各个点成为顶点）
 */
public class Vertex {
    private char label; // 这是顶点的名称，A B C D 等等顶点
    private boolean visited; // 是否已经被遍历过

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public char getLabel() {
        return label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public Vertex(char label){
        this.label = label;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "label=" + label +
                ", visited=" + visited +
                '}';
    }
}
