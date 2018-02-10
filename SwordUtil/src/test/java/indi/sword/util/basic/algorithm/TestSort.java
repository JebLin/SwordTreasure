package indi.sword.util.basic.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sword
 *         总结：
 *
 *         一、稳定性:
 * 假定在待排序的记录序列中，存在多个具有相同的关键字的记录，若经过排序，这些记录的相对次序保持不变，即在原序列中， ri=rj，且ri在rj之前，而在排序后的序列中，ri仍在rj之前，则称这种排序算法是稳定的；否则称为不稳定的。
 *
 *         　 稳定：冒泡排序、插入排序、二分插入排序、归并排序和基数排序
 *
 *         　 不稳定：选择排序、快速排序、希尔排序、堆排序
 *
 *         二、平均时间复杂度
 *
 *         　　O(n^2):直接插入排序，简单选择排序，冒泡排序。
 *
 *         　　在数据规模较小时（9W内），直接插入排序，简单选择排序差不多。当数据较大时，冒泡排序算法的时间代价最高。性能为O(n^2)
 *         的算法基本上是相邻元素进行比较，基本上都是稳定的。
 *
 *         　　O(nlogn):快速排序，归并排序，希尔排序，堆排序。
 *
 *         　　其中，快排是最好的， 其次是归并和希尔，堆排序在数据量很大时效果明显。
 *
 *         三、排序算法的选择
 *
 *         　　1.数据规模较小
 *
 *         　　（1）待排序列基本序的情况下，可以选择直接插入排序；
 *
 *         　　（2）对稳定性不作要求宜用简单选择排序，对稳定性有要求宜用插入或冒泡
 *
 *         　　2.数据规模不是很大
 *
 *         　　（1）完全可以用内存空间，序列杂乱无序，对稳定性没有要求，快速排序，此时要付出log（N）的额外空间。
 *
 *         　　（2）序列本身可能有序，对稳定性有要求，空间允许下，宜用归并排序
 *
 *         　　3.数据规模很大
 *
 *         　　（1）对稳定性有求，则可考虑归并排序。
 *
 *         　　（2）对稳定性没要求，宜用堆排序
 *
 *         　　4.序列初始基本有序（正序），宜用直接插入，冒泡
 */

public class TestSort {
    public static void main(String[] args) {
        int[] a = { 1, 2, 49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 2, 1};

        System.out.println("排序之前：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        // 一、插入排序

        // 1.直接插入排序
        //straightInsertSort(a);

        // 2.二分排序法
//         midInsertSort(a);

        // 3.希尔排序
//         shellSort(a);

        // 二、选择排序

        // 1.普通选择排序法
        // selectionSort(a);

        // 2.堆排序
//         heapSort(a);

        // 三、交换排序

        // 1.冒泡排序
//         bubbleSort(a);

        // 2.快速排序
        // quickSort1(a);

        // 四、归并排序
//         mergeSort(a);

        // 五、基数排序
        //radixSort(a);

        System.out.println();
        System.out.println("排序之后：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

    }

    /**
     *
     * 一、插入排序
     *
     * ①直接插入排序（从后向前找到合适位置后插入，插入已经排好的数组）
     *
     * 基本思想：每步将一个待排序的记录，按其顺序码大小插入到前面已经排序的字序列的合适位置（从后向前找到合适位置后），直到全部插入排序完为止。
     *
     * 文件初态不同时，直接插入排序所耗费的时间有很大差异。若文件初态为正序，则每个待插入的记录只需要比较一次就能够找到合适的位置插入，
     *
     * 故算法的时间复杂度为O(n)，这时最好的情况。若初态为反序，则第i个待插入记录需要比较i+1次才能找到合适位置插入，
     *
     * 故时间复杂度为O(n2)，这时最坏的情况。
     *
     * 直接插入排序的平均时间复杂度为O(n2)。
     *
     *
     */

    public static void straightInsertSort(int[] a) {

        // 直接插入排序
        for (int i = 1; i < a.length; i++) {
            // 待插入元素
            int temp = a[i];
            int j;
            for(j = i - 1; j >= 0 && a[j] >= temp;j--){
                // 将大于temp的往后移动一位
                a[j+1] = a[j];
            }
            a[j + 1] = temp;
        }
    }

    /**
     *
     * 一、插入排序
     *
     * ②二分法插入排序（按二分法找到合适位置插入，插入已经排好的数组）
     *
     * 基本思想：二分法插入排序的思想和直接插入一样，只是找合适的插入位置的方式不同，这里是按二分法找到合适的位置，可以减少比较的次数。
     *
     * 二分法插入排序也是稳定的。
     *
     * 二分插入排序的比较次数与待排序记录的初始状态无关，仅依赖于记录的个数。当n较大时，比直接插入排序的最大比较次数少得多。
     *
     * 但大于直接插入排序的最小比较次数。算法的移动次数与直接插入排序算法的相同，最坏的情况为n2/2，最好的情况为n，平均移动次数为O(n2)。
     */
    public static void midInsertSort(int[] a) {

        for (int i = 1; i < a.length; i++) {
            int temp = a[i];
            int left = 0;
            int right = i - 1;
            int mid = 0;
            while (left <= right) {
                mid = (left + right) / 2;
                if (temp < a[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            System.out.println("i = " + i + ",left = " + left + ",right = "
                    + right + ",mid = " + mid);
            for (int j = i - 1; j >= left; j--) {
                a[j + 1] = a[j];
            }
//            if (left != i) {
                a[left] = temp;
//            }
        }
    }

    /**
     *
     * 一、插入排序
     *
     * ③希尔排序
     *
     * 基本思想：先取一个小于n的整数d1作为第一个增量，把文件的全部记录分成d1个组。
     *
     * 所有距离为d1的倍数的记录放在同一个组中。先在各组内进行直接插入排序；然后，取第二个增量d2<d1重复上述的分组和排序，
     *
     * 直至所取的增量dt=1(dt<dt-l<…<d2<d1)，即所有记录放在同一组中进行直接插入排序为止。该方法实质上是一种分组插入方法。
     *
     * @param a
     *
     *            希尔排序是不稳定的。
     *
     *            希尔排序的时间性能优于直接插入排序，原因如下：
     *
     *            　　（1）当文件初态基本有序时直接插入排序所需的比较和移动次数均较少。
     *
     *            　　（2）当n值较小时，n和n2的差别也较小，即直接插入排序的最好时间复杂度O(n)和最坏时间复杂度0(n2)差别不大。
     *
     *            　　（3）在希尔排序开始时增量较大，分组较多，每组的记录数目少，故各组内直接插入较快，后来增量di逐渐缩小，分组数逐渐减少，
     *            而各组的记录数目逐渐增多，但由于已经按di-1作为距离排过序，使文件较接近于有序状态，所以新的一趟排序过程也较快。
     *
     *            　　因此，希尔排序在效率上较直接插人排序有较大的改进。
     *
     *            　　希尔排序的平均时间复杂度为O(nlogn)。
     *
     *
     */

    public static void shellSort(int[] a) {
        int d = a.length;
        while (true) {
            d = d / 2;
            for (int x = 0; x < d; x++) {
                // 此处进行每个小组进行直接插入排序
                for (int i = x + d; i < a.length; i = i + d) {
                    int temp = a[i];
                    int j;
                    for (j = i - d; j >= 0 && a[j] > temp; j = j - d) {
                        a[j + d] = a[j];
                    }
                    a[j + d] = temp;
                }
            }
            if (d == 1) {
                break;
            }
        }
    }

    /**
     *
     * 二、选择排序
     *
     * ①简单的选择排序
     *
     * 基本思想：在要排序的一组数中，选出最小的一个数与第一个位置的数交换；然后在剩下的数当中再找最小的与第二个位置的数交换，
     * 如此循环到倒数第二个数和最后一个数比较为止。
     *
     * 简单选择排序是不稳定的排序。
     *
     * 时间复杂度：T(n)=O(n2)。
     */

    public static void selectionSort(int[] a) {
        // 简单的选择排序
        for (int i = 0; i < a.length; i++) {
            int min = a[i];
            int n = i; // 最小数的索引
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < min) { // 找出最小的数
                    min = a[j];
                    n = j;
                }
            }
            a[n] = a[i];
            a[i] = min;
        }

    }

    /**
     *
     * 二、选择排序
     *
     * ②堆排序（大根堆为例）
     *
     * 　思想:初始时把要排序的数的序列看作是一棵顺序存储的二叉树，调整它们的存储序，使之成为一个堆，这时堆的根节点的数最大。
     *
     *   然后将根节点与堆的最后一个节点交换（把最大的都往后放），然后对前面(n-1)个数重新调整使之成为堆。依此类推，直到只有两个节点的堆，
     *
     *  并对 它们作交换，最后得到有n个节点的有序序列。从算法描述来看，堆排序需要两个过程，一是建立堆，二是堆顶与堆的最后一个元素交换位置。
     *
     * 所以堆排序有两个函数组成。一是建堆的渗透函数，二是反复调用渗透函数实现排序的函数。
     *
     * @param a
     *
     *            堆排序也是一种不稳定的排序算法。
     *
     *            堆排序优于简单选择排序的原因：
     *
     *            　　直接选择排序中，为了从R[1..n]中选出关键字最小的记录，必须进行n-1次比较，然后在R[2..n]
     *            中选出关键字最小的记录，又需要做n-2次比较。
     *
     *            事实上，后面的n-2次比较中，有许多比较可能在前面的n-1次比较中已经做过，但由于前一趟排序时未保留这些比较结果，
     *            所以后一趟排序时又重复执行了这些比较操作。
     *
     *            　　堆排序可通过树形结构保存部分比较结果，可减少比较次数。
     *
     *            　　堆排序的最坏时间复杂度为O(nlogn)。堆序的平均性能较接近于最坏性能。由于建初始堆所需的比较次数较多，
     *            所以堆排序不适宜于记录数较少的文件。
     */

    public static void heapSort(int[] a) {

        // 循环建堆
        for (int i = 0; i < a.length - 1; i++) {

            int lastIndex = a.length - 1 - i;// 最后一个索引

            buildMaxHeap(a, lastIndex); // 建堆

            swap(a, 0, lastIndex); // 交换堆顶和最后一个元素
            //System.out.println(Arrays.toString(a));
        }
    }

    // 对data数组从0到lastIndex建大顶堆
    public static void buildMaxHeap(int[] data, int lastIndex) {
        // 从lastIndex处节点（最后一个节点）的父节点开始
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            // k保存正在判断的节点
            int k = i;

            // 如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                // k节点的左子节点的索引
                int biggerIndex = 2 * k + 1;
                // 如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    // 若果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        // biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }

                // 如果k节点的值小于其较大的子节点的值
                if (data[k] < data[biggerIndex]) {
                    // 交换他们
                    swap(data, k, biggerIndex);
                    // 将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }



    // 交换
    public static void swap(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    /**
     *
     * 三、交换排序
     *
     * ①冒泡排序
     *
     * 1、基本思想：在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对相邻的两个数依次进行比较和调整，
     * 让较大的数往下沉，较小的往上冒。即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
     * @param a
     *
     *            冒泡排序是一种稳定的排序方法。
     *
     *            •若文件初状为正序，则一趟起泡就可完成排序，排序码的比较次数为n-1，且没有记录移动，时间复杂度是O(n)
     *            •若文件初态为逆序，则需要n-1趟起泡，每趟进行n-i次排序码的比较，且每次比较都移动三次，比较和移动次数均达到最大值∶O(
     *            n2)
     *            •起泡排序平均时间复杂度为O(n2)
     *
     *
     */

    public static void bubbleSort(int[] a) {

        // 冒泡排序
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                // 这里-i主要是每遍历一次都把最大的i个数沉到最底下去了，没有必要再替换了
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    /**
     *
     * 三、交换排序
     *
     * ②快速排序
     *
     * 1、基本思想：选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,
     * 一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分。
     *
     * 4、分析
     *
     * 快速排序是不稳定的排序。
     * 快速排序的时间复杂度为O(nlogn)。
     * 当n较大时使用快排比较好，当序列基本有序时用快排反而不好。
     *
     */

    public static void quickSort1(int[] a) {
        if (a.length > 0) {
            quickSort2(a, 0, a.length - 1);
        }
    }

    public static void quickSort2(int[] a, int low, int high) {
        if (low < high) { // 如果不加这个判断递归会无法退出导致堆栈溢出异常
            int middle = getMiddle(a, low, high);
            // 递归
            quickSort2(a, low, middle - 1);
            quickSort2(a, middle + 1, high);
        }
    }

    public static int getMiddle(int[] a, int low, int high) {
        int temp = a[low];// 基准元素
        /*
        * 基准元素找到合适的位置再站回去，否则一直在外边跳舞
        * 先把基准元素和右边的元素比较，如果右边有元素比它小，那么就把左边下标元素替换成右边下标元素。（从右往左比较）
        * 然后再继续把基准元素与其左边的元素比较，如果有元素比它大，那么就把右边下标元素替换成左边下标元素。（从左往右比较）
        * 直到跑到中间
        */
        while (low < high) {
            while (low < high && a[high] >= temp) {
                high--;
            }
            a[low] = a[high];
            while (low < high && a[low] <= temp) {
                low++;
            }
            a[high] = a[low];
        }
        a[low] = temp;
        return low;
    }

    /**
     *
     * 四、归并排序
     *
     * 1、基本思想:归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，每个子序列是有序的。
     * 然后再把有序子序列合并为整体有序序列。
     *
     * @param a
     *
     *            　归并排序是稳定的排序方法。
     *
     *            　归并排序的时间复杂度为O(nlogn)。
     *
     *            　速度仅次于快速排序，为稳定排序算法，一般用于对总体无序，但是各子项相对有序的数列。
     */

    public static void mergeSort(int[] a) {
        mergeS(a, 0, a.length - 1);
    }

    public static void mergeS(int[] a, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            // 对左边进行递归拆分
            mergeS(a, left, middle);
            // 对右边进行递归拆分
            mergeS(a, middle + 1, right);
            // 合并
            merge(a, left,middle,right);
        }
    }

    public static void merge(int[] a, int left, int middle,int right) {
        int[] tempArr = new int[a.length]; // 申请一个新的数组
        int mid = middle + 1; // 右边的起始位置
        int tempIndex = left; // 暂时不变的，以后用来赋值给原数组的
        int third = left; // 这个是改变的，以后用来逐个增加的

        // 从两个数组中选取较小的数放入中间数组，左右两边会有一个数组先over
        while (left <= middle && mid <= right) {
            if (a[left] <= a[mid]) {
                tempArr[third++] = a[left++];
            } else {
                tempArr[third++] = a[mid++];
            }
        }
        // 将剩余的部分放入中间数组
        while (left <= middle) {
            tempArr[third++] = a[left++];
        }

        while (mid <= right) {
            tempArr[third++] = a[mid++];
        }

        // 将中间数组复制回原数组,修改原数组
        while (tempIndex <= right) {
            a[tempIndex] = tempArr[tempIndex++];
        }
    }

    /**
     *
     * 五、基数排序
     *
     * 1、基本思想：将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面补零。
     *
     * 然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成以后,数列就变成一个有序序列。
     *
     *      基数排序是稳定的排序算法
     *            　　 基数排序的时间复杂度为O(d(n+r)),d为位数，r为基数。
     */

    public static void radixSort(int[] array) {
        // 找到最大数，确定要排序几趟
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        // 判断位数
        int times = 0;
        while (max > 0) {
            max = max / 10;
            times++;
        }
        // 建立十个队列
        List<ArrayList> queue = new ArrayList<ArrayList>();
        for (int i = 0; i < 10; i++) {
            ArrayList queue1 = new ArrayList();
            queue.add(queue1);
        }

        // 进行times次分配和收集
        for (int i = 0; i < times; i++) {
            // 分配
            for (int j = 0; j < array.length; j++) {
                int x = array[j] % (int) Math.pow(10, i + 1)
                        / (int) Math.pow(10, i);
                ArrayList queue2 = queue.get(x);
                queue2.add(array[j]);
                queue.set(x, queue2);
            }

            // 收集
            int count = 0;
            for (int j = 0; j < 10; j++) {
                while (queue.get(j).size() > 0) {
                    ArrayList<Integer> queue3 = queue.get(j);
                    array[count] = queue3.get(0);
                    queue3.remove(0);
                    count++;
                }
            }
        }
    }
}

