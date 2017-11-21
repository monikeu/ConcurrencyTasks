package domowe.producerConsumerNElems;

import domowe.ProducerConsumerAsynchronous.MonitorIf;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MonitorN implements MonitorIf {
    private final Lock lock = new ReentrantLock();
    private final Condition firstProd = lock.newCondition();
    private final Condition restProd = lock.newCondition();
    private final Condition firstCons = lock.newCondition();
    private final Condition restCons = lock.newCondition();
    private boolean firstProdWaits;
    private boolean firstConsWaits;

    private int free;
    private int taken;
    private int[] numbers;
    private final int N;

    MonitorN(int M){
        free = 2*M;
        taken = 0;
        numbers = new int[2*M];
        N = 2*M;

        firstProdWaits = false;
        firstConsWaits = false;

        for(int i=0;i<N;i++){
            numbers[i] = 0;
        }
    }

    public void put(int amount){
        lock.lock();
        try{

            if(firstProdWaits){
                restProd.await();
            }

            while(amount > free) {
                firstProdWaits = true;
                firstProd.await();
            }

            int j = 0;
            for(int i=0;i<amount;i++){
                while(numbers[j] != 0) j++;
                numbers[j] = 1;
            }
            free-=amount;
            taken+=amount;
//            System.out.println("\t Produced " + amount + "  free " + free + "  taken " + taken);
            restProd.signal();
            firstCons.signal();

        } catch(InterruptedException exc) {
            exc.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void take(int amount){
        lock.lock();
        int k =0;
        long time = 0;
        try {

            time = System.currentTimeMillis();
            if(firstConsWaits){
                restCons.await();
            }

            while(amount > taken){
                firstConsWaits = true;
                k++;
                firstCons.await();
            }
            time = time - System.currentTimeMillis();
            if(amount ==  N/2-1)
                System.out.println("Took " + amount + " waited " + k + " times\n");

            int j = 0;
            for(int i=0;i<amount;i++){
                while(numbers[j] != 1) j++;
                numbers[j] = 0;
            }
            free+=amount;
            taken-=amount;
//            System.out.println("\t Consumed " + amount + "  free " + free + "  taken " + taken);
            restCons.signal();
            firstProd.signal();

        } catch(InterruptedException exc) {
            exc.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private synchronized void printTable(){
        for(int i=0;i<N;i++){
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }

}