package domowe.producerConsumerAsynchronousVersionWithQueues;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Monitor4341  {
    private final Lock lock = new ReentrantLock();
    private final Condition BLOCKED = lock.newCondition();

    private int[] buffer; // 0 free, 1 occupied, 2 full
    private final int N;
    private int toInsert;
    private int toGet;

    Monitor4341(int M, int[] buffer) {
        toGet =0;
        toInsert=0;
        N = M;
        this.buffer = buffer;
    }

    public int put_begining() {
        int i;
        lock.lock();
        try {
            if (buffer[toInsert] != 0) {
                i = N;
            } else {
                i = toInsert;
                buffer[toInsert] = 2;
                toInsert = (toInsert + 1) % N;
            }
        } finally {
            lock.unlock();
        }
        return i;
    }

    public void put_end(int i) {
        lock.lock();
        try {
            buffer[i] = 2;
            if (i == toGet) {
                BLOCKED.signal();
            }
        } finally {
            System.out.println("Produced " + i + "\n");
            lock.unlock();
        }
    }

    public int get_beginning() {
        int i;
        lock.lock();
        try {
            if (buffer[toGet] != 2) {
                try {
                    BLOCKED.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i = toGet;
            toGet = (toGet + 1) % N;

        } finally {
            lock.unlock();
        }
        return i;
    }

    public void get_end(int i){
        lock.lock();
        try {
            buffer[i] = 0;
            toInsert=i;
        }finally {
            System.out.println("Consumed " + toGet + "\n");
            lock.unlock();
        }
    }

}