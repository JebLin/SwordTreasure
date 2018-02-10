package indi.sword.util.basic.algorithm;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 12:35 2018/2/7
 * @Modified By
 */

// 判断一个范围内的数字是不是质数
public class TestPrimary {

    public static void main(String[] args) {
        int start = 2;
        int end = 100;
        getPrimary(start,end);
    }

    // 得到范围内的质数
    public static void getPrimary(int start,int end){
        
        for (int i = start; i < end; i++) {
            if(isPrimary(i)){
                System.out.println(i);
            }
        }
    }

    // 判断是否为质数
    private static boolean isPrimary(int i) {
        if(i == 2 || i == 3){
            return true;
        }
        for (int j = 2; j <= (int) Math.sqrt(i); j++) {
            if(i % j == 0){
                return false;
            }
        }
        return true;
    }

}
