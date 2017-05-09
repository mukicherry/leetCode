package mqy.api.javaBasicTest;

/**
 * Created by maoqiyun on 2017/4/21.
 */
public class StringRegexTest {

    public static void main(String[] args) {
        String input = "/api/sdk/resource/authmenu";
        boolean isMatch = input.matches("/api/sdk/resource/.*");
        System.out.print(isMatch);
    }
}
