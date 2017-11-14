package domowe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Monitor4341  {
    private final Lock lock = new ReentrantLock();
    private final Condition BLOCKED = lock.newCondition();

    private int[] numbers; // 0 free, 1 occupied, 2 full
    private final int N;
    private int toInsert;
    private int toGet;
    int free ;

    Monitor4341(int M) {

        toGet =0;
        toInsert=0;
        N = 2 * M;
        numbers = new int[N];
        free = N;

        for (int i = 0; i < N; i++) {
            numbers[i] = 0;
        }
    }

    public int put_begining() {
        int i=0;
        lock.lock();
        try {
            if(free>0){
                i = toInsert;
                numbers[toInsert] = 2;
                toInsert = (toInsert + 1) % N;
                free-=1;
            } else {
                System.out.println("Lost informations\n");
            }
        } finally {
            lock.unlock();
        }
        return i;
    }

    public void put_end(int i) {
        lock.lock();
        try {
            numbers[i] = 2;
            if (i == toGet) {
                BLOCKED.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public int get_beginning() {
        int i;
        lock.lock();
        try {
            if (numbers[toGet] != 2) {
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
        numbers[i] = 0;
    }


    private synchronized void printTable() {
        for (int i = 0; i < N; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }


}