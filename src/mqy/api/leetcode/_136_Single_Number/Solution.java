package mqy.api.leetcode._136_Single_Number;

/**
 * Created by maoqiyun on 2017/4/12.
 */
public class Solution {
    public int singleNumber(int[] nums) {
        int ans =0;

        int len = nums.length;
        for(int i=0;i!=len;i++)
            ans ^= nums[i];

        return ans;
    }
}
