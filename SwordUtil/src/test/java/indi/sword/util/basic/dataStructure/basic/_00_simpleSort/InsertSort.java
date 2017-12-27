package indi.sword.util.basic.dataStructure.basic._00_simpleSort;

/**
 * @Description: 简单插入排序 在已经排序好的数组内找到合适位置插入数据。
 * @Author: rd_jianbin_lin
 * @Date:20:47 2017/12/11
 */
public class InsertSort {
	
	public static void sort(long[] arr) {
		long tmp = 0;
		
		for(int i = 1; i < arr.length; i++) {
			tmp = arr[i];
			int j = i;
			while(j > 0 && arr[j] >= tmp) {
				arr[j] = arr[j - 1];
				j--;
			}
			arr[j] = tmp;
		}
	}
}
