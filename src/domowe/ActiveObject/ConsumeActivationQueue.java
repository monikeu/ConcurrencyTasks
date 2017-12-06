package domowe.ActiveObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class ConsumeActivationQueue extends ActivationQueue{

    private Queue<ConsumerMethodRequest> queue = new ConcurrentLinkedQueue<>();

    public boolean guardOneRequest(int inFullQ){
        if(!queue.isEmpty()){
            return queue.peek().guard(inFullQ);
        }
        return false;
    }

    @Override
    void enqueue(MethodRequest methodRequest) {
        queue.add((ConsumerMethodRequest)methodRequest);
    }

    @Override
    ConsumerMethodRequest dequeue() {
        return queue.poll();
    }
}
