package domowe.ActiveObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    private static ConsumeActivationQueue consumerQueue = new ConsumeActivationQueue();
    private static ProduceActivationQueue producerQueue = new ProduceActivationQueue();

    public static Queue<Integer> freeQ = new ConcurrentLinkedQueue<>();
    public static Queue<Integer> fullQ = new ConcurrentLinkedQueue<>();

    public static int N = 1000;
    public static int free = N, full = 0;


    public Scheduler() {
        for (int i = 0; i < N; i++) {
            freeQ.add(i);
        }
    }

    public static boolean reserveForProducer(int n) {
        lock.lock();
        if (free >= n) {
            free -= n;
            lock.unlock();
            return true;
        } else {
            lock.unlock();
            return false;
        }

    }

    public static boolean reserveForConsumer(int n) {
        lock.lock();

        if (full >= n) {
            full -= n;
            lock.unlock();
            return true;
        } else {
            lock.unlock();
            return false;
        }
    }

    public void enqueueInProducersQueue(MethodRequest methodRequest) {
        producerQueue.enqueue(methodRequest);
    }

    public void enqueueInConsumersQueue(MethodRequest methodRequest) {
        consumerQueue.enqueue(methodRequest);
    }


    public static void giveBackFree(int howMany) {
        lock.lock();
        free += howMany;
        lock.unlock();
    }

    public static void giveBackFull(int howMany) {
        lock.lock();
        full += howMany;
        lock.unlock();
    }

    @Override
    public void run() {
        while (true) {
            if (producerQueue.guardOneRequest(free)) {
                new Thread(producerQueue.dequeue()).start();
            }
            if (consumerQueue.guardOneRequest(full)) {
                new Thread(producerQueue.dequeue()).start();
            }
        }
    }

}
