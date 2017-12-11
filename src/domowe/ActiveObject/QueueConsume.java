package domowe.ActiveObject;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

class QueueConsume extends ActivationQueue{

    private Queue<ConsumerMethodRequest> queue = new LinkedBlockingQueue<>();

    @Override
    void enqueue(MethodRequest methodRequest) {
        queue.add((ConsumerMethodRequest)methodRequest);
    }

    @Override
    ConsumerMethodRequest dequeue() {
        return queue.poll();
    }

    ConsumerMethodRequest peek(){return queue.peek();}

    boolean isEmpty(){return queue.isEmpty();};
}
