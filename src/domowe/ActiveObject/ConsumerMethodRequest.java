package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumerMethodRequest implements MethodRequest {

    public int n;
    public Future future;
    public long start;

    public ConsumerMethodRequest(int n, Future future, long start){
        this.future=future;
        this.n=n;
        this.start=start;
    }

    @Override
    public boolean guard(int inFullQ) {
        return n <= inFullQ;
    }

}
