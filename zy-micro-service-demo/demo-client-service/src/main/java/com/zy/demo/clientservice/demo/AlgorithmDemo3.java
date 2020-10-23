package com.zy.demo.clientservice.demo;

/**
 * @author ZY
 * @date 2020/10/16 16:41
 * @Description:
 * @Version 1.0
 */
public class AlgorithmDemo3 {
    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * 示例 1:
     * <p>
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * <p>
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * <p>
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串
     */
    public static Integer lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int ans = 0;
        int[] map = new int[128];
        for (int i = 0, j = 0; i < s.length(); i++) {
            // 将 i 所指字符出现的次数加 1
            map[s.charAt(i)]++;
            while (map[s.charAt(i)] > 1) {
                // 注意：需要将 j 所指字符出现的次数减 1
                map[s.charAt(j)]--;
                j++;
            }
            // 不断更新结果，取最大的值
            ans = Math.max(ans, i - j + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        Integer pwwkew = lengthOfLongestSubstring("pwwkewoplm");
        System.out.println(pwwkew);
    }
}
