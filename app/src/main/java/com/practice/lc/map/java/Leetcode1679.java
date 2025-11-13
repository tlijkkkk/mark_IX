package com.practice.lc.map.java;

import java.util.HashMap;
import java.util.Map;

public class Leetcode1679 {
    public int maxNumKSumPairs(int[] nums, int k) {
        final Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }

        int result = 0;
        for (int num : nums) {
            Integer v = map.get(num);
            if (v == null) continue;
            if (v == 1) {
                map.remove(num);
            } else {
                map.put(num, v - 1);
            }
            Integer v2 = map.get(k - num);
            if (v2 == null) {
                // revert v1
                map.put(num, v);
                continue;
            }
            if (v2 == 1) {
                map.remove(k - num);
            } else {
                map.put(k - num, v2 - 1);
            }

            result++;
        }
        return result;
    }

}
