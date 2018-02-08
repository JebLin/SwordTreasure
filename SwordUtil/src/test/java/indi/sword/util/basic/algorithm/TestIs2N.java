package indi.sword.util.basic.algorithm;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 13:03 2018/2/8
 * @Modified By
 */
// 判断一个数是不是2的n次幂
public class TestIs2N {
    public static void main(String[] args) {
        System.out.println(is2N_2(1023));
    }

    // 第一种方法：移位算判断
    public static boolean is2N(int temp){
        int n2 = 1;
        while(temp > n2){
            n2 <<= 1;
            if(temp == n2){
                return true;
            }
        }
        return false;
    }

    // 第二种方法：与运算。必定与其自身减1后的数相“与”为假，比如8,1000&0111=0000,
    public static boolean is2N_2(int temp){
        return ((temp - 1) & temp) == 0 ? true:false ;
    }
}
