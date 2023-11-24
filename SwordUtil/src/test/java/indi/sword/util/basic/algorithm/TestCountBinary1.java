package indi.sword.util.basic.algorithm;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 14:45 2018/2/8
 * @Modified By
 */
// 求取整数对应二进制数中1的个数
public class TestCountBinary1 {
    public static void main(String[] args) {
        System.out.println(count_1(1124));
        System.out.println(count_2(1124));
        System.out.println(count_3(1124));

    }

    // 第一种方法： temp最后一位和 1进行与运算，是1就1个，是0就0个，直到最后为 0
    public static int count_1(int temp){
        int num = 0;
        while(temp != 0){
            num += (temp & 0x01);
            temp >>= 1;
        }
        return num;
    }

    // 第二种方法： temp 与 temp - 1 进行与运算，由于每次减1 都会有0的出现，于是乎每次消灭掉一个 1，直到最后为0
    public static int count_2(int temp){
        int num = 0;
        while(temp != 0){
            temp &= (temp - 1);
            num++;
        }
        return num;
    }

    // 第三种方法： temp mod 2,除2取余，这种方法面试0分
    public static int count_3(int temp){
        int num = 0;
        while(temp != 0){
            num += (temp % 2 == 0 ? 0 : 1); // 余数是0 ？是1的话，就加1
            temp /= 2;
        }
        return num;
    }


}
