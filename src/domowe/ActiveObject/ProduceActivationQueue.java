package domowe.ActiveObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProduceActivationQueue extends ActivationQueue{
    private Queue<ProducerMethodRequest> queue = new ConcurrentLinkedQueue<>();

    public boolean guardOneRequest(int inFreeQ){
        if(!queue.isEmpty()){
            return queue.peek().guard(inFreeQ);
        }
        return false;
    }

    @Override
    void enqueue(MethodRequest methodRequest) {
        queue.add((ProducerMethodRequest)methodRequest);
    }

    @Override
    ProducerMethodRequest dequeue() {
        return queue.poll();
    }
}
