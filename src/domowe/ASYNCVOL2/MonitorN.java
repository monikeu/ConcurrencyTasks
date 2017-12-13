package domowe.ASYNCVOL2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


// dwa locki muszą być, problem z awaitami i sygnałami
// 4 condition ??? po dwa na jedną i to ma chodzić
class MonitorN {
    private int count = 0;

    private final ReentrantLock prodLock = new ReentrantLock();
    private final ReentrantLock consLock = new ReentrantLock();

    private final int N;

    public  int done =0;

    private boolean firstConsWaits, firstProdWaits;

    // dwa locki, jeden dla producerów, drugi dla consumerów, jeden dla firsta drugi dla reszty
    private Condition waitFirstProd = prodLock.newCondition();
    private Condition waitRestProd = prodLock.newCondition();
    private Condition waitFirstCons = consLock.newCondition();
    private Condition waitRestCons = consLock.newCondition();


    private Queue<Integer> freeQ;
    private Queue<Integer> fullQ;
    private Writer writerCons;
    private Writer writerProd;

    MonitorN(int N) {
        this.N = 2 * N;

        freeQ = new ConcurrentLinkedQueue<>();
        fullQ = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < this.N; i++) {
            freeQ.add(i);
        }

        try {
            writerCons = new BufferedWriter((new FileWriter("asyncCons.csv")));
            writerCons.write("Time, Amount\n");
            writerProd = new BufferedWriter((new FileWriter("asyncProd.csv")));
            writerProd.write("Time, Amount\n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Integer> putBegin(int n) {

        prodLock.lock();

        List<Integer> res = new ArrayList<>();
        try {

            if(firstProdWaits){
                waitRestProd.await();
            }
            int a;
            firstProdWaits = true;
            while (freeQ.size() < n) {
                waitFirstProd.await();
            }
            for (int j = 0; j < n; j++) {
                Integer i;
                i = freeQ.poll();
                res.add(i);

            }
            if(!prodLock.hasWaiters(waitRestProd)){
                firstProdWaits = false;
            }
            waitRestProd.signal();
            waitFirstProd.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            prodLock.unlock();
        }
        return res;
    }

    public void putEnd(long start, List<Integer> list) {
        prodLock.lock();
        try {
            fullQ.addAll(list);
            int i;
            consLock.lock();
            waitFirstCons.signal();
        } finally {
            long l = System.currentTimeMillis()-start;
            try {
                writerProd.write(l + "," + list.size() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            done++;
            consLock.unlock();
            prodLock.unlock();
        }
    }

    public List<Integer> getBegin(int n) {

        List<Integer> res = new ArrayList<>();
        consLock.lock();
        try {

            if(firstConsWaits){
                waitRestCons.await();
            }
            int a;
            firstConsWaits = true;
            while (fullQ.size() < n) {
                waitFirstCons.await();
            }
            for (int j = 0; j < n; j++) {
                Integer i = fullQ.poll();
                res.add(i);
            }
            if(!consLock.hasWaiters(waitRestCons)){
                firstConsWaits = false;
            }
            waitFirstCons.signal();
            waitRestCons.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consLock.unlock();
        }
        return res;
    }

    public void getEnd(long start, List<Integer> list) {
        consLock.lock();
        try {
            int i;
            freeQ.addAll(list);
            prodLock.lock();
            waitFirstProd.signal();

        } finally {
            long l = System.currentTimeMillis()-start;
            try {
                writerCons.write(l + "," + list.size() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            done++;
            prodLock.unlock();
            consLock.unlock();
        }
    }

    public void closeFiles(){
        try {
            writerCons.close();
            writerProd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}