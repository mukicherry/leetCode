package mqy.api.javaBasicTest;

/**
 * Created by maoqiyun on 15/11/30.
 */
public abstract class AbstractB implements InterfaceC{


    @Override
    public int interfaceMethod() {
        return 11;
    }

    public String solidMethod(){
        return "solidMethod";
    }

    public abstract String abstractMethod();
}
