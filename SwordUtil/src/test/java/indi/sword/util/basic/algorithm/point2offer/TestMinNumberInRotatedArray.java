package indi.sword.util.basic.algorithm.point2offer;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 12:19 AM 30/03/2018
 * @MODIFIED BY
 */
/*
    题目： 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为旋转。 输入一个递增的排序的数组的一个旋转，输出旋转数组的最小元素。
     例如数组｛3，4，5，1，2｝为{1,2,3,4,5}的一个旋转，该数组的最小元素为1.
 */
/*
    这道题最直观的解法并不难，从头到尾遍历一次，我们就能找到最小的元素。这种思路的时间复杂度为O（n)。但是这个思路没有利用输入的旋转数组的特性，肯定达不到面试官的要求。
我们注意到旋转之后的数组实际上可以划分为两个排序的子数组，而且前面的子数组的元素都是大于或者等于后面子数组的元素。我们还注意到最小的元素刚好是这两个子数组的分界线。在排序的数组中我们可以利用二分查找来实现O（logn)的查找。本题给出的数组在一定程度上是排序的，因此我们可以试着用二分查找的思路来寻找这个最小的元素。
以前面的例子为例，我们先把第一个指针指向第0个元素，把第二个指针指向第4个元素，如图所示。位于两个指针中间（在数组的下标是2）的数字是5，它大于第一个指针指向的数字。因此中间数字5一定位于第一个递增字数组中，并且最小的数字一定位于它的后面。因此我们可以移动第一个指针让它指向数组的中间。
此时位于这两个指针中间的数字为1，它小于第二个指针指向的数字。因此这个中间数字为1一定位于第二个递增子数组中，并且最小的数字一定位于它的前面或者它自己就是最小的数字。因此我们可以移动第二个指针指向两个指针中间的元素即下标为3的元素。
此时两个指针的距离为1，表明第一个指针已经指向了第一个递增子数组的末尾，而第二个指针指向第二个递增子数组的开头。第二个子数组的第一个数字就是最小的数字，因此第二个指针指向的数字就是我们查找的结果。

    上述方法是否就一定够完美了呢？面试官会告诉你其实不然。他将提示我们再仔细分析小标leftIndex和rightIndex分别和途中P1和P2相对应）的两个数相同的情况。在前面，当着两个数相同，并且它们中间的数相同的也相同时，我们把IndexMid赋给了leftIndex，也就是认为此时最小的数字位于中间数字的后面。是不是一定一样？
我们再来看一个例子。数组｛1，0，1，1，1｝和数组｛1，1，1，0，1｝都可以堪称递增排序数组｛0，1，1，1，1｝的旋转，图2分别画出它们由最小数字分隔开的两个子数组。
这两种情况中，第一个指针和第二个指针指向的数字都是1，并且两个指针中间的数字也是1，这3个数字相同。在第一种情况中，中间数字（下标为2)位于后面是子数组；在第二种情况中，中间数字（下标为2）位于前面的子数组中。因此，当两个指针指向的数字及它们中间的数字三者相同的时候，我们无法判断中间的数字是位于前面的子数组中还是后面的子数组中国，也无法移动两个指针来缩小查找的范围。此时，我们不得不采用顺序查找的方法。


 */
public class TestMinNumberInRotatedArray {
    // https://blog.csdn.net/jsqfengbao/article/details/47108069#reply
    public static void main(String[] args) {
        int[] intArr = {6,7,8,9,1,2,3,4,5};
//        int[] intArr = {1,0,1,1,1};
//        int[] intArr = {2,2,2,2,2,0,1,2,2};
        int min = minInReversingList(intArr);
        System.out.println(min);
    }

    /**
     * @Description
     *      找到旋转数组中最小的数字
     * @Author jeb_lin
     * @Date 11:56 AM 30/03/2018
     * @MODIFIED BY
     */
    private static int minInReversingList(int[] intArr) {
        int left = 0;
        int right = intArr.length - 1;


        while (left + 1 < right){
            int mid = ( left + right ) / 2;
            // 这是一种特殊情况，非递减数列的，例如 数组｛1，0，1，1，1｝和数组｛1，1，1，0，1｝
            if(intArr[mid] == intArr[left] && intArr[mid] == intArr[right]){
                return midInOrder(intArr,left,right);
            }
            // intArr[mid]比intArr[left]大的话，说明intArr[mid]还处于左边的递增队列，left向右移动
            // intArr[mid]比intArr[right]小的话，说明intArr[mid]还处于右边的递增队列，right向左移动
            // 当left紧紧挨着right的时候，right就是那个min了
            if(intArr[mid] >= intArr[left]){
                left = mid;
            }else {
                right = mid;
            }
        }
        return intArr[right];
    }

    /**
     * @Description
     *      这是一种特殊情况，非递减数列的，例如 数组｛1，0，1，1，1｝和数组｛1，1，1，0，1｝,
     *      那么就按顺序查找
     * @Author jeb_lin
     * @Date 2:06 PM 30/03/2018
     * @MODIFIED BY
     */
    private static int midInOrder(int[] intArr, int left, int right) {
        int minimum = intArr[left];
        for (int i = left + 1; i <= right; i++) {
            if(minimum > intArr[i]){
                minimum = intArr[i];
            }
        }
        return minimum;
    }
}
