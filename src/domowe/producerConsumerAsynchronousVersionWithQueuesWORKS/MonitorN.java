package domowe.producerConsumerAsynchronousVersionWithQueuesWORKS;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class MonitorN {

    private final ReentrantLock lock = new ReentrantLock();

    private final int N;

    private Condition waitRestCons = lock.newCondition();
    private Condition waitRestProd = lock.newCondition();

    private Condition waitFirstCons = lock.newCondition();
    private Condition waitFirstProd = lock.newCondition();

    private Queue<Integer> freeQ;
    private Queue<Integer> fullQ;

    private boolean firstProdWaits= false, firstConsWaits=false;

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

            if(firstProdWaits){
                waitRestProd.await();
            }

            firstProdWaits = true;
            while (freeQ.size() < n) {
                waitFirstProd.await();
            }
            for (int j = 0; j < n; j++) {
                Integer i;
                i = freeQ.poll();
                res.add(i);

            }
            if(!lock.hasWaiters(waitRestProd)){
                firstProdWaits = false;
            }

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
            waitFirstCons.signal();
            waitRestProd.signal();
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> getBegin(int n) {

        List<Integer> res = new ArrayList<>();
        lock.lock();
        try {

            if(firstConsWaits){
                waitRestCons.await();
            }
            firstConsWaits = true;
            while (fullQ.size() < n) {
                waitFirstCons.await();
            }
            for (int j = 0; j < n; j++) {
                Integer i = fullQ.poll();
                res.add(i);
            }
            if(!lock.hasWaiters(waitRestCons)){
                firstConsWaits = false;
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
            waitFirstProd.signal();
            waitRestCons.signal();

        } finally {
            lock.unlock();
        }
    }
}