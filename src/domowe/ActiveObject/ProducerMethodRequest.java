package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class ProducerMethodRequest implements MethodRequest {

    public int n;
    public Future future;

    public ProducerMethodRequest(int n, Future future){
        this.future=future;
        this.n=n;
    }

    @Override
    public boolean guard(int inFreeQ) {
      return n <= inFreeQ;
    }

}
