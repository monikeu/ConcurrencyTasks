package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class ActivationQueue {
    List<MethodRequest> queue;

    ActivationQueue(int n){
        queue = new ArrayList<>(n);
    }

    public void enqueue(MethodRequest methodRequest, int i){
        queue.add(i, methodRequest);
    }

    public MethodRequest dequeue(int i){
        queue.get(i);
        return null;
    }
}
