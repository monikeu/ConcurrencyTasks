package domowe.ActiveObject;

import java.util.Queue;

public  abstract class  ActivationQueue {
    private Queue<MethodRequest> queue;
    abstract void enqueue(MethodRequest methodRequest);
    abstract MethodRequest dequeue();
}
