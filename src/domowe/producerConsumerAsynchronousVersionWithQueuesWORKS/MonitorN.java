package domowe.producerConsumerAsynchronousVersionWithQueuesWORKS;

import java.io.*;
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

    private BufferedWriter writerProd;
    private BufferedWriter writerCons;

    private boolean firstProdWaits= false, firstConsWaits=false;
    private String consFileName;
    private String prodFileName;

    MonitorN(int M, int threads, String consFileName, String prodFileName) {
        this.N = 2 * M;
        this.consFileName = consFileName;
        this.prodFileName = prodFileName;

        freeQ = new ConcurrentLinkedQueue<>();
        fullQ = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < this.N; i++) {
            freeQ.add(i);
        }

        try {
            writerCons = new BufferedWriter((new FileWriter(consFileName, true)));
//            writerCons.write("Time, Threads, SleepTime, Buffsize, Amount\n");
            writerProd = new BufferedWriter((new FileWriter(prodFileName, true)));
//            writerProd.write("Time, Threads, SleepTime, Buffsize, Amount\n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> putBegin(int n, int sleepTime, int threads) {

        long start = System.currentTimeMillis();
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
            long time = System.currentTimeMillis() - start;
            writerProd.write(time + "," + threads + "," + sleepTime + "," + N +  "," + n +"\n");
            if(!lock.hasWaiters(waitRestProd)){
                firstProdWaits = false;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return res;
    }

    public void putEnd( List<Integer> list) {
        lock.lock();
        try {
            fullQ.addAll(list);
            waitFirstCons.signal();
            waitRestProd.signal();
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> getBegin(int n, int sleepTime, int threads) {

        List<Integer> res = new ArrayList<>();
        long start = System.currentTimeMillis();
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

            long time = System.currentTimeMillis() - start;
            writerCons.write(time + "," + threads + "," + sleepTime + "," + N +  "," + n +"\n");

            if(!lock.hasWaiters(waitRestCons)){
                firstConsWaits = false;
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }
        return res;
    }

    public void getEnd( List<Integer> list) {
        lock.lock();
        try {
            freeQ.addAll(list);
            waitFirstProd.signal();
            waitRestCons.signal();

        } finally {
            lock.unlock();
        }
    }
    public void close() {
        try {
            writerProd.close();
            writerCons.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}