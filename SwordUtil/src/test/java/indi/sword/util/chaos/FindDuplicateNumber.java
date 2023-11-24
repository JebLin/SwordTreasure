package indi.sword.util.chaos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jeb_lin
 * 00:38 2023/11/6
 */
public class FindDuplicateNumber {
    // 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
    //假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。

    public static int findDuplicate(int[] nums) {
        int left = 1;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }
            if (count > mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 4, 2, 3};
        // 给你5个数字，这5个数字肯定是在1-4之间的，那么找出这个数字
        // 那么因为就在 1-4 之间，那么mid = (1+5)/2 = 3
        // 那么如果数组中的数字，有3个小于mid 2，那么重复元素肯定在2左边，反之就在左边
        // 那么就先看下小于 mid 2的数量有多少。

        System.out.println(findDuplicate(arr));
    }


    /*
        给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false 。

        示例 1：

        输入：nums = [1,2,3,1], k = 3
        输出：true
        示例 2：

        输入：nums = [1,0,1,1], k = 1
        输出：true
        示例 3：

        输入：nums = [1,2,3,1,2,3], k = 2
        输出：false

     */

}

