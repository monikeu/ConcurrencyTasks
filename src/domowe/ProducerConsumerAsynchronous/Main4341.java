package domowe.ProducerConsumerAsynchronous;

import java.util.Random;

public class Main4341 {
    private final static int M = 10;
    private final static int producersNumber = 10;

    public static void main(String[] args) {

        int[] buffer = new int[M];
        for (int i = 0; i < M; i++) {
            buffer[i] = 0;
        }

        Monitor4341 monitorN = new Monitor4341(M, buffer);

        Thread producers[] = new Thread[producersNumber];

        Random random = new Random();

        Thread customer = new Thread(new Consumer4341(random.nextInt(M), monitorN,buffer));
        customer.start();

        for(int i=0;i<producersNumber;i++){
            producers[i] = new Thread(new Producer4341(random.nextInt(M), monitorN,buffer));
            producers[i].start();
        }
    }
}
