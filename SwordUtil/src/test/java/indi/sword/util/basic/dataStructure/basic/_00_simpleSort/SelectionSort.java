package indi.sword.util.basic.dataStructure.basic.simpleSort;

/**
 * @Description: 简单选择排序，从左往右找到最小的，依次排序。交换次数肯定是比冒泡排序少的
 * @Author: rd_jianbin_lin
 * @Date:20:48 2017/12/11
 */
public class SelectionSort {
	
	public static void sort(long[] arr) {
		int k = 0;
		long tmp = 0;
		for(int i = 0; i < arr.length - 1; i++) {
			k = i;
			for(int j = i; j < arr.length; j++) {
				if(arr[j] < arr[k]) {
					k = j;
				}
			}
			tmp = arr[i];
			arr[i] = arr[k];
			arr[k] = tmp;
		}
	}
}
