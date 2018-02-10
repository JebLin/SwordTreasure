package indi.sword.util.basic.algorithm;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 10:33 2018/2/7
 * @Modified By
 */
/*
    斐波那契数列指的是这样一个数列 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233，377，610，987，1597，2584，4181，6765，10946，17711，28657，46368........
    这个数列从第3项开始，每一项都等于前两项之和
    f(0) = 1 , f(1) = 1.
 */
public class TestFibonacci {

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            System.out.print(fbnc1(i) + ",");
        }
        System.out.println();
        for (int i = 0; i < 25; i++) {
            System.out.print(fbnc2(i) + ",");
        }
        System.out.println();
        for (int i = 0; i < 25; i++) {
            System.out.print(fbnc3(i) + ",");
        }

    }

    // 递归方法，效率低. 这种面试0分
    public static int fbnc1(int n){
        if(n == 0 || n == 1){
            return 1;
        }else{
            return fbnc1(n - 1) + fbnc1(n - 2);
        }
    }

    // 采用中间值，效率高
    public static int fbnc2(int n){
        if(n == 0 || n == 1){
            return 1;
        }
        int f0 = 1;
        int f1 = 1;
        int f2 = 0;
        for(int i = 2; i <= n; i++){
            f2 = f0 + f1;
            f0 = f1;
            f1 = f2;
        }
        return f2;
    }

    // 采用数组形式
    public static int fbnc3(int n){ // 传进来的是下标
        if(n == 0 || n == 1){
            return 1;
        }
        int[] arr = new int[n + 1];
        arr[0] = 1;
        arr[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n];
    }



}
