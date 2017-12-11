package domowe.ActiveObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueProduce extends ActivationQueue{
    private Queue<ProducerMethodRequest> queue = new ConcurrentLinkedQueue<>();

    @Override
    void enqueue(MethodRequest methodRequest) {
        queue.add((ProducerMethodRequest)methodRequest);
    }

    @Override
    ProducerMethodRequest dequeue() {
        return queue.poll();
    }

    ProducerMethodRequest peek(){return queue.peek();}

    boolean isEmpty(){return queue.isEmpty();};
}
