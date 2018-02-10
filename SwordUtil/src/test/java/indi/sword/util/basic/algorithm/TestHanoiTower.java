package indi.sword.util.basic.algorithm;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 10:17 2018/2/8
 * @Modified By
 */
/*
    1.河内之塔
        说明
        河内之塔(Towers of Hanoi)是法国人M.Claus(Lucas)于1883年从泰国带至法国的，河内为越战时
        北越的首都，即现在的胡志明市；1883年法国数学家 Edouard Lucas曾提及这个故事，据说创世
        纪时Benares有一座波罗教塔，是由三支钻石棒（Pag）所支撑，开始时神在第一根棒上放置64
        个由上至下依由小至大排列的金盘（Disc），并命令僧侣将所有的金盘从第一根石棒移至第三根
        石棒，且搬运过程中遵守大盘子在小盘子之下的原则，若每日仅搬一个盘子，则当盘子全数搬
        运完毕之时，此塔将毁损，而也就是世界末日来临之时。
        解法如果柱子标为ABC，要由A搬至C，在只有一个盘子时，就将它直接搬至C，当有两个盘
        子，就将B当作辅助柱。如果盘数超过2个，将第三个以下的盘子遮起来，就很简单了，每次处
        理两个盘子，也就是：A->B、A ->C、B->C这三个步骤，而被遮住的部份，其实就是进入程式
        的递回处理。事实上，若有n个盘子，则移动完毕所需之次数为2^n - 1，所以当盘数为64时，则
        64
        如果对这数字没什幺概念，就假设每秒钟搬一个盘子好了，也要约5850亿年左右。
 */
public class TestHanoiTower {
    public static void main(String[] args) {
        int[] arr = new int[3];
        // 原本是放在 A 的,假设移动到 B.
        hanoi(arr,arr.length,"A","B","C");
    }

    public static void hanoi(int[] arr, int length, String from, String to, String temp){
        if(length == 1){
            moveBottomBar(arr,arr.length,from,to);
            return;
        }

        // 1、把上面的 n - 1 个，从 from -> temp
        hanoi(arr,length - 1,from,temp,to);
        // 2、把最下面的那根放到 B，从 from ->to
        moveBottomBar(arr,length,from,to);
        // 3、把刚刚拿走的那一坨移回来，temp -> to
        hanoi(arr,length - 1,temp,to,from);
    }

    public static void moveBottomBar(int[] arr, int length,String from,String to){
        System.out.println(from + " -> " + to);

    }
}
