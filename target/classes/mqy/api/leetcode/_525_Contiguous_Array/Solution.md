Description:
> Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
> 给出一个二进制数组，请确定其有着等数量0和1的连续子数组的最大长度

关键词：子数组，连续


The idea is to change 0 in the original array to -1. 
思路这样的：把原数组中所有元素0变更为-1

Thus, if we find SUM[i, j] == 0 then we know there are even number of -1 and 1 between index i and j.
这样，我们就会发现如果从第i个元素到第j个元素的累加和sum等于0，则说明从下标i到j有相等数目的-1和1，对原数组来说，就是相等数目的0和1.

Also put the sum to index mapping to a HashMap to make search faster.
而HashMap则可以用于把累加和sum放置于key的位置，以便更好的索引。
key：sum
value：子数组的长度

