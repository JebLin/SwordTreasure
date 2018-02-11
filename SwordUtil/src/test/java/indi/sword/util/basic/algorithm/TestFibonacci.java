
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
        System.out.println();
        for (int i = 0; i < 25; i++) {
            System.out.print(fbnc4(i) + ",");
        }
        System.out.println();
        for (int i = 0; i < 25; i++) {
            System.out.print(fbnc_futher(i) + ",");
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

    // 这种中间值都不用给
    public static int fbnc3(int n){
        if(n == 0 || n == 1){
            return 1;
        }
        int pre = 1;
        int result = 1;
        for(int i = 2; i <= n; i++){

            result = pre + result;
            pre = result - pre;

        }
        return result;
    }


    // 采用数组形式
    public static int fbnc4(int n){ // 传进来的是下标
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


    /*
        斐波拉契数列加强版——时间复杂度O(1)，空间复杂度O(1)

        对于斐波拉契经典问题，我们都非常熟悉，通过递推公式F(n) = F(n - 1) + F(n - 2)，我们可以在线性时间内求出第n项F(n)，
        现在考虑斐波拉契的加强版，我们要求的项数n的范围为int范围内的非负整数，请设计一个高效算法，计算第n项F(n)。
        第一个斐波拉契数为F(0) = 1。
        负数的话，全部为0 F(-|num|) = 0;
        给定一个非负整数，请返回斐波拉契数列的第n项，为了防止溢出，请将结果Mod 1000000007。

        数学知识补充：
           线性代数：
           1、二阶行列式
           | a11  a12 |
           |          |   = a11*a22 - a12*a22  "对角线法则"
           | a21  a22 |

           2、矩阵相乘运算： 矩阵C的元素 Cij 即为矩阵A 的第i行元素与矩阵B的第j列对应元素乘积的和。
           | a11  a12 |   | b11  b12 |     | a11*b11 + a12*b21  a11*b22 + a12*b22 |
           |          | * |          |  =  |                                      |
           | a21  a22 |   | b21  b22 |     | a21*b11 + a22*b21  a21*b12 + a22*b22 |

        考虑下面三个矩阵：
        | F(n)   F(n-1) |       | 1  1  |       | F(n-1)  F(n-2) |
        |               |   *   |       |  =    |                |
        | F(n-1) F(n-2) |       | 1  0  |       | F(n-2)  F(n-3) |
        分别设为S(n), M, S(n-1).

               | F(1)  F(0)  |     | 1  1 |
        S(1) = |             |  =  |      |  = M  (important: 关系1)
               | F(0)  F(-1) |     | 1  0 |
        由上面的矩阵相乘可以得到：
            S(n) = M * S(n-1)
                 = (M ^ 2) * S(n-2)
                 = (M ^ 3) * S(n-3)
                 = M ^ (n-1) * S(1)
                 = M ^ n;   （important: 关系2）
    */
    // 采用数组形式
    public static long fbnc_futher(int n){ // 传进来的是下标
        if(n == 0 || n == 1){
            return 1;
        }

        long[][] M = {{1,1},{1,0}};
        long[][] result =  {{1,1},{1,0}};
        for (int i = 0; i < n - 1 ; i++) {
            result = getMulti(result,M);
        }
        return result[0][0]; // 看S(n)的第一个元素

    }

    // 矩阵相乘
    private static long[][] getMulti(long[][] result,long[][] m) {
        long c00 = result[0][0] * m[0][0] + result[0][1] * m[1][0];
        long c01 = result[0][0] * m[0][1] + result[0][1] * m[1][1];
        long c10 = result[1][0] * m[0][0] + result[1][1] * m[1][0];
        long c11 = result[1][0] * m[0][1] + result[1][0] * m[1][1];
        long[][] returnResult = {{c00,c01},{c10,c11}};
        return returnResult;
    }


}
