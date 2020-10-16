package com.zy.demo.clientservice.demo;

import java.util.HashMap;

/**
 * @author ZY
 * @date 2020/10/16 16:41
 * @Description:
 * @Version 1.0
 */
public class AlgorithmDemo1 {

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] indexs = new int[2];

        // 建立k-v ，一一对应的哈希表
        HashMap<Integer,Integer> hash = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(hash.containsKey(nums[i])){
                indexs[0] = i;
                indexs[1] = hash.get(nums[i]);
                return indexs;
            }
            // 将数据存入 key为补数 ，value为下标
            hash.put(target-nums[i],i);
        }
        return indexs;
    }

    public static void main(String[] args) {
        int[] a = new int[]{11, 7, 2, 15};
        int b = 9;
        int[] ints = twoSum(a, b);
        System.out.println(ints[0]+","+ints[1]);
    }
}
