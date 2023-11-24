package indi.sword.util.basic.algorithm.point2offer;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jeb_lin
 * 15:06 2023/11/24
 */
/*
    给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
    你返回所有和为 0 且不重复的三元组。
    注意：答案中不可以包含重复的三元组。

        示例 1：

        输入：nums = [-1,0,1,2,-1,-4]
        输出：[[-1,-1,2],[-1,0,1]]
        解释：
        nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
        nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
        nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
        不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
        注意，输出的顺序和三元组的顺序并不重要。
        示例 2：

        输入：nums = [0,1,1]
        输出：[]
        解释：唯一可能的三元组和不为 0 。
        示例 3：

        输入：nums = [0,0,0]
        输出：[[0,0,0]]
        解释：唯一可能的三元组和为 0 。

        提示：

        3 <= nums.length <= 3000
        -105 <= nums[i] <= 105
 */

public class ThreeNum {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4, 2, 2, 1, 3};
        System.out.println(JSON.toJSONString(threeSum(nums)));

    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        // [-4,-1,-1,0,1,2,2,2]
        // [-4,-1,-1,0,1,1,2,2,2,3]
        for (int i = 0; i < nums.length; i++) {
            int left = i + 1;
            if (left >= nums.length) {
                break;
            }
            if (i > 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < 0) {
                    left++;
                } else if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;

                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
