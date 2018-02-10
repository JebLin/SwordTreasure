package indi.sword.util.basic.algorithm.point2offer;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 14:57 2018/2/9
 * @Modified By
 */
/*
    在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
    请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class TestSearchArray {
    public static void main(String[] args) {

        int[][] arr = getArr(20,20,1000);
        printArr(arr);
        System.out.println(200 + " - " + haveTheNumber(200,arr));
    }

    // 输出矩阵
    private static void printArr(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + ",");
            }
            System.out.println();
        }
    }

    // 判断是否有数字
    /*
        思路
         矩阵是有序的，从左下角来看，向上数字递减，向右数字递增，
         因此从左下角开始查找，当要查找数字比左下角数字大时。右移
         要查找数字比左下角数字小时，上移
     */
    private static boolean haveTheNumber(int target ,int[][] arr) {
        int currentRow = arr.length - 1;
        int currentColumn = 0;
        int currentNumber = arr[currentRow][currentColumn]; // 最左下角开始找
        while(currentRow >= 0 && currentColumn <= arr[0].length - 1){ // 当还在这个矩阵里面的话
            if(target == currentNumber){
                System.out.println("bingo ... index -> " + currentRow + "-" + currentColumn);
                return true;
            }else if(target > currentNumber){
                ++currentColumn;
                if (currentColumn > arr[0].length - 1){
                    return false;
                }else{
                    currentNumber = arr[currentRow][currentColumn];
                }
            }else{
                --currentRow;
                if(currentRow < 0){
                    return false;
                }else {
                    currentNumber = arr[currentRow][currentColumn];
                }
            }
        }
        return false;
    }

    /**
     * @Description 生成符合规定的数组，输入几行几列，范围。返回一个int数组。有序的
     * @Author rd_jianbin_lin
     * @Date 15:09 2018/2/9
     * @Modified By
     */
    // 我全部排序好，那就是从上到下都递增，从左到右都递增的
    private static int[][] getArr(int row, int column, int rang) {
        Random random = ThreadLocalRandom.current();
        int[][] arr = new int[row][column];

        int[] tempArr = new int[row * column];
        for (int i = 0; i < tempArr.length; i++) {
            tempArr[i] = random.nextInt(rang);
        }
        int index = 0;
        sort(tempArr);

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = tempArr[index++];
            }
        }
        return arr;
    }


    /**
     * @Description 把生成好的数组排个序
     * @Author rd_jianbin_lin
     * @Date 15:13 2018/2/9
     * @Modified By
     */
    private static void sort(int[] tempArr) {
        for (int i = 1; i < tempArr.length; i++) {
            int temp = tempArr[i];
            int j = i -1;
            for ( ; j >= 0 && temp < tempArr[j]; j--) {
                tempArr[j + 1] = tempArr[j];
            }
            tempArr[j + 1] = temp;
        }
    }




}
