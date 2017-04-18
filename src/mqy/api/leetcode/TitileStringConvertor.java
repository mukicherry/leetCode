package mqy.api.leetcode;
/**
 * Created by maoqiyun on 2017/4/14.
 */
public class TitileStringConvertor {

    public String convert(String input) {
        String firstConvert = input.replace(".","");
        String secondConvert = firstConvert.replace(" ","_");
        StringBuilder builder = new StringBuilder("_");
        builder.append(secondConvert);
        return builder.toString();

    }

    public static void main(String[] args) {
        TitileStringConvertor convertor = new TitileStringConvertor();
        String input = "438. Find All Anagrams in a String";
        System.out.print(convertor.convert(input));
    }
}
