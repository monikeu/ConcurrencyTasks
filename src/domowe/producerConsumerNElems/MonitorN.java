package domowe.producerConsumerNElems;

import java.io.*;
import java.util.Random;
import java.util.concurrent.locks.Condition;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.abs;

class MonitorN implements MonitorIf {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition firstProd = lock.newCondition();
    private final Condition restProd = lock.newCondition();
    private final Condition firstCons = lock.newCondition();
    private final Condition restCons = lock.newCondition();
    private BufferedWriter writerProd;
    private BufferedWriter writerCons;
    private boolean firstProdWaits;
    private boolean firstConsWaits;
    private String consFileName;
    private String prodFileName;

    private int free;
    private int threads;
    private int taken;
    private int[] numbers;
    private final int N;
    private Random random = new Random();

    MonitorN(int M, int threads, String consFileName, String prodFileName) {
        this.consFileName = consFileName;
        this.prodFileName = prodFileName;
        free = 2 * M;
        this.threads = threads;
        taken = 0;
        numbers = new int[2 * M];
        N = 2 * M;

        firstProdWaits = false;
        firstConsWaits = false;

        for (int i = 0; i < N; i++) {
            numbers[i] = 0;
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

    public void put(int amount, int sleepTime) {
        lock.lock();
        long start = System.currentTimeMillis();
        try {

            if (firstProdWaits) {
                restProd.await();
                Thread.sleep(abs(random.nextInt(sleepTime+1)));
            }

            firstProdWaits = true;
            while (amount > free) {
                firstProd.await();
                Thread.sleep(abs(random.nextInt(sleepTime+1)));
            }

            int j = 0;
            for (int i = 0; i < amount; i++) {
                while (numbers[j] != 0) j++;
                numbers[j] = 1;
            }
            long time = System.currentTimeMillis() - start;
            writerProd.write(time + "," + threads + "," + sleepTime + "," + N +  "," + amount +"\n");

            free -= amount;
            taken += amount;

            if (!lock.hasWaiters(restProd))
                firstProdWaits = false;

            restProd.signal();
            firstCons.signal();

        } catch (InterruptedException exc) {
            exc.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void take(int amount, int sleepTime) {
        lock.lock();
        long start = System.currentTimeMillis();
        int k = 0;
        try {
            if (firstConsWaits) {
                restCons.await();
                Thread.sleep(abs(random.nextInt(sleepTime+1)));
            }

            firstConsWaits = true;
            while (amount > taken) {
                firstCons.await();
                Thread.sleep(abs(random.nextInt(sleepTime+1)));
                k++;
            }

            int j = 0;
            for (int i = 0; i < amount; i++) {
                while (numbers[j] != 1) j++;
                numbers[j] = 0;
            }
            long time = System.currentTimeMillis() - start;
            writerCons.write(time + "," + threads + "," + sleepTime + "," + N +  "," + amount +"\n");


            free += amount;
            taken -= amount;

            if (!lock.hasWaiters(restCons))
                firstConsWaits = false;

            restCons.signal();
            firstProd.signal();

        } catch (InterruptedException exc) {
            exc.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private synchronized void printTable() {
        for (int i = 0; i < N; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
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