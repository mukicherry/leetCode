Given a List of words, return the words that can be typed using letters of alphabet 
on only one row's of American keyboard like the image below.

[picture](https://leetcode.com/static/images/problemset/keyboard.png)


Example 1:
Input: ["Hello", "Alaska", "Dad", "Peace"]
Output: ["Alaska", "Dad"]

Note:
- You may use one character in the keyboard more than once.
- You may assume the input string will only contain letters of alphabet.


思路
- 枚举出键盘上每一行的字母
- HashMap存的是<字符-其所在键盘的行数>
