package indi.sword.util.basic.algorithm;

import org.hibernate.cfg.annotations.MapKeyColumnDelegator;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 12:03 2018/2/7
 * @Modified By
 */
public class Test2SortArray {
    public static void main(String[] args) {

    }
    public static boolean test(int[][] arr,int temp){
        for (int i = arr.length - 1; i >= 0 ; i--) {
            if(temp > arr[i][0]){
                for (int j = 0; j < arr[i].length; j++) {
                    if (arr[i][j] == temp){
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
