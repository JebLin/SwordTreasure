package indi.sword.util.chaos;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * @author jeb_lin
 * 11:13 AM 2019/9/20
 */
public class Test02 {
    public static void main(String[] args) {
        int[] inputArr = {1, 2, 3};
        splitK(inputArr, 5);

        int[] inputArr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        splitK(inputArr2, 5);

        int[] inputArr3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        splitK(inputArr3, 5);

    }

    private static void splitK(int[] inputArr, int k) {
        List<List<Integer>> result = Lists.newArrayList();
        int index = 0;
        if (k >= inputArr.length) {
            for (int i = 0; i < k; i++) {
                List<Integer> tempList = new ArrayList<>();
                if (index <= inputArr.length - 1) {
                    tempList.add(inputArr[index++]);
                }
                result.add(tempList);
            }
            System.out.println(JSON.toJSONString(result));
            return;
        }

        int arrayCount = inputArr.length / k; // 10 / 3 = 3, 11 / 3 = 3
        int leisure = inputArr.length % k;// 10 % 3 = 1,11 % 3 = 2

        for (int i = 0; i < k; i++) {
            result.add(new ArrayList<>());
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arrayCount; j++) {
                result.get(i).add(inputArr[index++]);
            }
            if (leisure > 0) {
                result.get(i).add(inputArr[index++]);
                leisure--;
            }
        }
        System.out.println(JSON.toJSONString(result));
    }
}
