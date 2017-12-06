package domowe.ActiveObject;

import domowe.producerConsumerAsynchronousVersionWithQueuesWORKS.Consumer4341;
import domowe.producerConsumerAsynchronousVersionWithQueuesWORKS.Producer4341;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Main {
    public static void main(String[] args) {
        int N = 500;

        int producersNumber = 10;
        Thread producers[] = new Thread[producersNumber];
        int consumersNumber =10;
        Thread consumers[] = new Thread[consumersNumber];

        List<Integer> buffer = new ArrayList<>();
        Random random = new Random();

        Proxy p = new Proxy();
        p.runScheduler();

        for(int i=0;i<consumersNumber;i++){
            consumers[i] = new Thread(new Consumer(abs(random.nextInt(100)),buffer,p));
            consumers[i].start();
        }

        for(int i=0;i<producersNumber;i++){
            producers[i] = new Thread(new Producer(abs(random.nextInt(100)) ,buffer,p));
            producers[i].start();
        }


    }
}
