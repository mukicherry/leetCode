package mqy.api.javaBasicTest;

/**
 * Created by maoqiyun on 16/1/7.
 */
public class ExceptionTest{

    public void fun() throws Exception {
        System.out.println("我只在方法签名后面throws Exception,不在方法体里throw new Exception");
    }

    public static void main(String[] args) {
        ExceptionTest exceptionTest = new ExceptionTest();
        try {
            exceptionTest.fun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
