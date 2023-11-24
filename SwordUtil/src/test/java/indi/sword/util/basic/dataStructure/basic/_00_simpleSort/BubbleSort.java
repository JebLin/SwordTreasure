package indi.sword.util.basic.dataStructure.basic._00_simpleSort;

/**
 * @Description:  冒泡排序，从左往右一个个滚动比较，最小的放左边
 * @Author: rd_jianbin_lin
 * @Date:20:47 2017/12/11
 */
public class BubbleSort {

	public static void sort(long[] arr) {
		long tmp = 0;
		for(int i = 0; i < arr.length - 1; i++) {
			for(int j = arr.length - 1; j > i; j--) {
				if(arr[j] < arr[j - 1]) {
					//进行交换
					tmp = arr[j];
					arr[j] = arr[j - 1];
					arr[j - 1] = tmp;
				}
			}
		}
	}
}
