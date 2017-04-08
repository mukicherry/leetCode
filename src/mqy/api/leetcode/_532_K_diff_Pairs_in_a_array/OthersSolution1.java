package mqy.api.leetcode._532_K_diff_Pairs_in_a_array;
import java.util.*;
/**
 * Created by maoqiyun on 2017/4/7.
 */
public class OthersSolution1 {
    public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0)   return 0;

        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i : nums) {
//            map.put(i, map.getOrDefault(i, 0) + 1)
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (k == 0) {
                //count how many elements in the array that appear more than twice.
                if (entry.getValue() >= 2) {
                    count++;
                }
            } else {
                if (map.containsKey(entry.getKey() + k)) {
                    count++;
                }
            }
        }

        return count;
    }
}
