package indi.sword.util.basic.algorithm;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 15:13 2018/2/8
 * @Modified By
 */
// 用随机函数生成100个在100到999之间的整数，设计程序统计这些三位数中十位是分别是0-9出现次数
//    时间复杂度：O(n)

public class TestCountTenNumber {
    public static void main(String[] args) {

        // 1、拿到100个随机数
        int numArr[] = generateNumArr();
        // 2、统计 0 - 9 出现的次数
        int numCount[] = getNumcount(numArr);
        // 3、输出信息
        printNumCount(numCount);

    }

    private static void printNumCount(int[] numCount) {
        int totalCount = 0;
        for (int i = 0; i < numCount.length; i++) {
            totalCount += numCount[i];
            System.out.println(i + " 出现个数为 --> " + numCount[i]);
        }
        System.out.println("验证下总个数 --> " + totalCount);
    }

    private static int[] getNumcount(int[] numArr) {
        int numCount[] = new int[10];
        for (int i = 0; i < numArr.length; i++) {
            numCount[numArr[i] / 10 % 10]++; // 121 / 10 = 12 , 12 % 10 = 2;
        }
        return numCount;
    }

    private static int[] generateNumArr() {
        Random random = ThreadLocalRandom.current();
//        random.nextInt(1); // 表示 [0,1);
        int numArr[] = new int[100];
        for (int i = 0; i < 100; i++) {
            numArr[i] = random.nextInt(100) * 8 + 100; // [0,800) + 100 -> [100,900);
        }
        return numArr;
    }


}
