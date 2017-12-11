package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumerMethodRequest implements MethodRequest {

    public int n;
    public Future future;

    public ConsumerMethodRequest(int n, Future future){
        this.future=future;
        this.n=n;
    }

    @Override
    public boolean guard(int inFullQ) {
        return n <= inFullQ;
    }

}
