package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class ProducerMethodRequest implements MethodRequest {

    public int n;
    public Future future;
    long start;

    public ProducerMethodRequest(int n, Future future, long start){
        this.future=future;
        this.n=n;
        this.start=start;
    }

    @Override
    public boolean guard(int inFreeQ) {
      return n <= inFreeQ;
    }

}
