package domowe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Monitor {
    private final Lock lock = new ReentrantLock();
    private Condition producerCanProduce = lock.newCondition();
    private Condition consumerCanConsume = lock.newCondition();

    private int free;
    private int taken;
    private int[] numbers;
    private final int N;


    Monitor(int M){
        free = 2*M;
        taken = 0;
        numbers = new int[2*M];
        N = 2*M;

        for(int i=0;i<N;i++){
            numbers[i] = 0;
        }

    }

    void put(int amount){
        lock.lock();
        try{
            while(amount > free) producerCanProduce.await();

            int j = 0;
            for(int i=0;i<amount;i++){
                while(numbers[j] != 0) j++;
                numbers[j] = 1;
            }

            free-=amount;
            taken+=amount;
            System.out.println("\t Produced " + amount + "  free " + free + "  taken " + taken);
            printTable();
            consumerCanConsume.signalAll();

        } catch(InterruptedException exc){
            exc.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void take(int amount){
        lock.lock();
        try{
            while(amount > taken) consumerCanConsume.await();

            int j = 0;
            for(int i=0;i<amount;i++){
                while(numbers[j] != 1) j++;
                numbers[j] = 0;
            }
            free+=amount;
            taken-=amount;
            System.out.println("\t Consumed " + amount + "  free " + free + "  taken " + taken);
            printTable();
            producerCanProduce.signalAll();

        } catch(InterruptedException exc){
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