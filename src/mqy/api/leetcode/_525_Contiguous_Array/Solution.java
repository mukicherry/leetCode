package mqy.api.leetcode._525_Contiguous_Array;
import java.util.*;
/**
 * Created by maoqiyun on 2017/4/8.
 */
public class Solution {
    public int findMaxLength(int[] nums) {
        //把原数组中所有元素0变更为-1
        for (int i = 0; i < nums.length ; i ++) {
            if (nums[i] == 0) {
                nums[i] = -1;
            }
        }
        int sum = 0;
        Map<Integer, Integer> sum_subArrayLen = new HashMap<>();
        int len = 0;
        sum_subArrayLen.put(sum,len);
        //计算累加和
        for (int i = 0; i < nums.length - 1; i ++) {
            sum = nums[i];
            len = 0;
            for (int j = i+1; j < nums.length; j ++) {
                sum = sum + nums[j];
                len = j - i + 1;
                if (sum_subArrayLen.containsKey(sum)) {
                    if (len > sum_subArrayLen.get(sum)) {
                        sum_subArrayLen.put(sum,len);
                    }
                }
                else {
                    sum_subArrayLen.put(sum,len);
                }
            }
        }
        return sum_subArrayLen.get(0);

    }

    public static void main(String[] args) {
//        int[] input = {0,1,0};
//        int[] input = {0,1};
        int[] input = {0,1,0,1,0,1,1,0,1,1,0,0,1};
        Solution solution = new Solution();
        int output = solution.findMaxLength(input);
        System.out.print(output);
    }
}
