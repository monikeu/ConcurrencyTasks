package domowe.producerConsumerAsynchronousVersionWithQueues;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MonitorN {
    private int count = 0;

    private final Lock lock = new ReentrantLock();

    private final int N;

    private Condition wait_cons = lock.newCondition();
    private Condition wait_prod = lock.newCondition();

    private Queue<Integer> freeQ;
    private Queue<Integer> fullQ;

    MonitorN(int N) {
        this.N = 2 * N;

        freeQ = new ConcurrentLinkedQueue<>();
        fullQ = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < this.N; i++) {
            freeQ.add(i);
        }

    }

    public List<Integer> putBegin(int n) {

        lock.lock();

        List<Integer> res = new ArrayList<>();
        try {
            while (freeQ.size() < n) {
                wait_prod.await();
            }
            for (int j = 0; j < n; j++) {
                Integer i;
                i = freeQ.poll();
                res.add(i);

            }
            wait_cons.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return res;
    }

    public void putEnd(List<Integer> list) {
        lock.lock();
        try {
            fullQ.addAll(list);
            wait_cons.signal();
        } finally {
            lock.unlock();

        }
    }

    public List<Integer> getBegin(int n) {

        List<Integer> res = new ArrayList<>();
        lock.lock();
        try {
            while (fullQ.size() < n) {
                wait_cons.await();
            }
            for (int j = 0; j < n; j++) {
                Integer i = fullQ.poll();
                res.add(i);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }
        return res;
    }

    public void getEnd(List<Integer> list) {
        lock.lock();
        try {

            freeQ.addAll(list);
            wait_prod.signal();

        } finally {
            lock.unlock();
        }
    }
}