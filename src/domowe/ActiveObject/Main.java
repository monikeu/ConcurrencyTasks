package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Main {
    public static void main(String[] args) {
        int N = 100;

        int producersNumber = 500;
        Thread producers[] = new Thread[producersNumber];
        int consumersNumber =500;
        Thread consumers[] = new Thread[consumersNumber];

        List<Integer> buffer = new ArrayList<>();
        Random random = new Random();

        Proxy p = new Proxy();
        p.runScheduler();

        for(int i=0;i<consumersNumber;i++){
            consumers[i] = new Thread(new Consumer(abs(random.nextInt(N)),buffer,p));
            consumers[i].start();
        }

        for(int i=0;i<producersNumber;i++){
            producers[i] = new Thread(new Producer(abs(random.nextInt(N)) ,buffer,p));
            producers[i].start();
        }

        try {
            p.joinScheduler();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0;i<consumersNumber;i++){
            try {
                consumers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<producersNumber;i++){
            try {
                producers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
