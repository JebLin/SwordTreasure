package indi.sword.util.basic.algorithm.point2offer;

import javafx.beans.binding.IntegerBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 测试两数之和
 *
 * @author jeb_lin
 * 14:16 2023/11/24
 */
/*
    给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
    你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
    你可以按任意顺序返回答案。
        示例 1：

        输入：nums = [2,7,11,15], target = 9
        输出：[0,1]
        解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
        示例 2：

        输入：nums = [3,2,4], target = 6
        输出：[1,2]
        示例 3：

        输入：nums = [3,3], target = 6
        输出：[0,1]


        提示：
        2 <= nums.length <= 104
        -109 <= nums[i] <= 109
        -109 <= target <= 109
        只会存在一个有效答案
 */
public class Test2NumberSum {
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};

//        print2NumberSum(nums, 9);

        int[] nums2 = {3, 2, 4, 1, 5};
        print2NumberSum(nums2, 6);

        int[] nums3 = {3, 3};
//        print2NumberSum(nums3, 6);

        System.out.println("----");

        print2NumberWithHash(nums2, 6);

    }

    /**
     * 时间复杂度N^2
     *
     * @param nums
     * @param target
     */
    private static void print2NumberSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length && nums[i] + nums[j] == target; j++) {
                System.out.println("i = " + i + ", j = " + j);
            }
        }
    }

    /**
     * 使用hash表
     * 时间复杂度N
     *
     * @param nums
     * @param target
     */
    private static int[] print2NumberWithHash(int[] nums, int target) {
        int[] result = new int[2];
        // 数值（可能重复) --> 下标index
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.get(nums[i]) == null) {
                map.put(nums[i], new ArrayList<>());
            }
            map.get(nums[i]).add(i);
        }

        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.keySet().contains(diff)) {
                List<Integer> indexList = map.get(diff);
                for (int j = 0; j < indexList.size(); j++) {
                    int index = indexList.get(j);
                    // 不打印自己  + 不打印重复组合
                    if (index != i && index > i) {
                        result[0] = i;
                        result[1] = index;
                        System.out.println("i = " + i + ", j = " + indexList.get(j));
                    }
                }
            }
        }
        return result;
    }
}
