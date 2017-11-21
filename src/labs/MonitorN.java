package labs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorN {
    final Lock lock = new ReentrantLock();
    final Condition firstProd  = lock.newCondition();
    final Condition firstCons = lock.newCondition();
    final Condition restProd  = lock.newCondition();
    final Condition restCons = lock.newCondition();
    Boolean firstProdWaits, firstConsWaits;

    final Integer[] items;
    int M, inBuffer, start;

    MonitorN(int M){
        this.M = M;
        inBuffer = 0;
        start = 0;
        firstProdWaits = false;
        firstConsWaits = false;
        items = new Integer[2*M];
    }

    public int size(){return this.M;}

    public void produce(int amount) throws InterruptedException {
        lock.lock();
        try {
            //sprawdzic, czy ktos juz czeka
            if(firstProdWaits)
                restProd.await();

            while( 2*M - inBuffer < amount) {
                firstProdWaits = true;
                firstProd.await();
            }
            //if there is a place
            for(int i=0; i<amount; i++){
                System.out.println( "zapisywanie w " + (start + inBuffer)% (2*M));
                items[ (start + inBuffer)% (2*M)] = 1;
                inBuffer++;
            }
            System.out.println("IN BUFFER: " + inBuffer);
            restProd.signal();
            firstCons.signal();

        } finally {
            lock.unlock();
        }
    }

    public void consume(int amount ) throws InterruptedException {
        lock.lock();
        try {
            //sprawdzic, czy jakis konsument czeka
            if(firstConsWaits)
                restCons.await();

            while(inBuffer < amount) {
                firstConsWaits = true;
                firstCons.await();
            }
            //mozna wziac
            for( int i=0; i< amount; i++){
                System.out.println("Zwalnianie w " + start%(2*M));
                items[start%(2*M)] = 0;
                inBuffer--;
                start++;
            }
            System.out.println("IN BUFFER: " + inBuffer);
            if(start >= 2*M )
                start = start%(2*M);

            restCons.signal();
            firstProd.signal();

        } finally {
            lock.unlock();
        }
    }
}
