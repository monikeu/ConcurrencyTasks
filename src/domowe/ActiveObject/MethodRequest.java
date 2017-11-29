package domowe.ActiveObject;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MethodRequest {

    private Callable method;
    private Future future;

    MethodRequest(Future future,Callable method){
        this.method = method;
        this.future = future;
    }
    public boolean guard(){ return false;}

    public void call() throws Exception {
        future = (Future)method.call();
    }

}
