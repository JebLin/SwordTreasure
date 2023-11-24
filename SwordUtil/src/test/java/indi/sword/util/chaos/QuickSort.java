package indi.sword.util.chaos;

/**
 * @author jeb_lin
 * 22:38 2023/11/6
 */
public class QuickSort {
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[low]; // 选择基准元素
        int left = low + 1;
        int right = high;

        while (true) {
            while (left <= right && arr[left] <= pivot) {
                left++;
            }
            while (left <= right && arr[right] >= pivot) {
                right--;
            }
            if (left <= right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            } else {
                break;
            }
        }

        int temp = arr[low];
        arr[low] = arr[right];
        arr[right] = temp;

        return right;
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8};
        quickSort(arr);

        System.out.print("Sorted array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}

